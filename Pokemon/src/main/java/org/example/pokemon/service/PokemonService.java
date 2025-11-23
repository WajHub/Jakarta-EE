package org.example.pokemon.service;

import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.request.PokemonEditRequest;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.User;
import org.example.pokemon.entity.UserRole;
import org.example.pokemon.interceptor.LogAction;
import org.example.pokemon.repository.impl.PokemonH2Repository;
import org.example.pokemon.repository.impl.SpeciesH2Repository;
import org.example.pokemon.repository.impl.UserH2Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@LocalBean
@Stateless
@NoArgsConstructor
public class PokemonService {

    private PokemonH2Repository pokemonH2Repository;
    private SpeciesH2Repository speciesH2Repository;
    private UserH2Repository userH2Repository;
    private DtoFunctionFactory factory;
    private SecurityContext securityContext;

    @Inject
    public PokemonService(PokemonH2Repository pokemonH2Repository, SpeciesH2Repository speciesH2Repository, UserH2Repository userH2Repository ,DtoFunctionFactory factory,
                          SecurityContext securityContext) {
        this.pokemonH2Repository = pokemonH2Repository;
        this.speciesH2Repository = speciesH2Repository;
        this.userH2Repository = userH2Repository;
        this.factory = factory;
        this.securityContext = securityContext;
    }

    @LogAction
    public void create(Pokemon pokemon){
        if(pokemonH2Repository.find(pokemon.getId()).isEmpty())
            pokemonH2Repository.create(pokemon);
    }

    @LogAction
    public void create(PokemonCreateRequest pokemonRequest) {
        pokemonRequest.setSpeciesId(pokemonRequest.getSpeciesId());
        var species = speciesH2Repository.find(pokemonRequest.getSpeciesId())
                .orElseThrow(() -> new IllegalArgumentException("Pokemon species with id " + pokemonRequest.getSpeciesId() + " not found"));
        var pokemonToCreate = factory.pokemonCreateRequestToPokemon().apply(pokemonRequest);
        pokemonToCreate.setSpecies(species);
        pokemonH2Repository.create(pokemonToCreate);
    }

    @LogAction
    public void create(UUID speciesId, PokemonCreateRequest pokemonRequest) {
        var species = speciesH2Repository.find(speciesId)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon species with id " + speciesId + " not found"));
        var pokemonToCreate = factory.pokemonCreateRequestToPokemon().apply(pokemonRequest);
        pokemonToCreate.setSpecies(species);

        User user = userH2Repository.findByUsername(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        pokemonToCreate.setOwner(user);
        pokemonH2Repository.create(pokemonToCreate);
    }

    @LogAction
    public void update(PokemonEditRequest pokemonRequest) {
        checkAdminRoleOrOwner(pokemonH2Repository.find(pokemonRequest.getId()));
        pokemonRequest.setSpeciesId(pokemonRequest.getSpeciesId());
        var species = speciesH2Repository.find(pokemonRequest.getSpeciesId())
                .orElseThrow(() -> new IllegalArgumentException("Pokemon species with id " + pokemonRequest.getSpeciesId() + " not found"));
        var pokemonToUpdate = factory.pokemonEditRequestToPokemon().apply(pokemonRequest);
        pokemonToUpdate.setSpecies(species);
        pokemonH2Repository.update(pokemonToUpdate);
    }

    @LogAction
    public void update(UUID speciesId, UUID pokemonId, PokemonEditRequest pokemonRequest) {
        checkAdminRoleOrOwner(pokemonH2Repository.find(pokemonId));
        pokemonRequest.setId(pokemonId);
        var species = speciesH2Repository.find(speciesId)
                .orElseThrow(() -> new NotFoundException("Pokemon species with id " + pokemonRequest.getSpeciesId() + " not found"));
        var pokemonToUpdate = factory.pokemonEditRequestToPokemon().apply(pokemonRequest);
        pokemonToUpdate.setSpecies(species);
        pokemonH2Repository.update(pokemonToUpdate);
    }

    @LogAction
    public List<PokemonResponse> getPokemons() {
        return pokemonH2Repository.findAll()
                .stream().map(factory.pokemontoPokemonResponse())
                .collect(Collectors.toList());
    }

    @LogAction
    public List<PokemonResponse> getPokemonsBySpeciesId(UUID speciesId) {
        if (securityContext.isCallerInRole(UserRole.ROLE_ADMIN)) {
            return pokemonH2Repository.findAllBySpeciesId(speciesId)
                    .stream().map(factory.pokemontoPokemonResponse())
                    .collect(Collectors.toList());
        }
        else if (securityContext.isCallerInRole(UserRole.ROLE_USER)) {
            return pokemonH2Repository.findAllBySpeciesIdAndUsername(speciesId, securityContext.getCallerPrincipal().getName())
                    .stream().map(factory.pokemontoPokemonResponse())
                    .collect(Collectors.toList());
        }
        else {
            throw new EJBAccessException("Caller not authorized.");
        }
    }

    @LogAction
    public PokemonResponse findById(UUID id) {
        checkAdminRoleOrOwner(pokemonH2Repository.find(id));
        return pokemonH2Repository.find(id)
                .map(factory.pokemontoPokemonResponse())
                .orElseThrow(() -> new NotFoundException("Pokemon FIUT with id " + id + " not found"));
    }

    @LogAction
    public Pokemon getPokemonById(UUID id) {
        return pokemonH2Repository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon with id " + id + " not found"));
    }

    @LogAction
    public void delete(UUID id) {
        checkAdminRoleOrOwner(pokemonH2Repository.find(id));
        Pokemon pokemon = pokemonH2Repository.find(id)
                .orElseThrow(() -> new NotFoundException("Pokemon with id " + id + " not found"));
        pokemonH2Repository.delete(pokemon);
    }

    private void checkAdminRoleOrOwner(Optional<Pokemon> pokemon) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRole.ROLE_ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRole.ROLE_USER)
                && pokemon.isPresent()
                && pokemon.get().getOwner().getUsername().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }

}
