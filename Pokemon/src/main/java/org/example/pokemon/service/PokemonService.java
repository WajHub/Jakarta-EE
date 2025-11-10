package org.example.pokemon.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.request.PokemonEditRequest;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.repository.impl.PokemonH2Repository;
import org.example.pokemon.repository.impl.PokemonRepository;
import org.example.pokemon.repository.impl.SpeciesH2Repository;
import org.example.pokemon.repository.impl.SpeciesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class PokemonService {

    private PokemonRepository pokemonRepository;
    private SpeciesRepository speciesRepository;
    private PokemonH2Repository pokemonH2Repository;
    private SpeciesH2Repository speciesH2Repository;
    private DtoFunctionFactory factory;

    @Inject
    public PokemonService(PokemonH2Repository pokemonH2Repository, SpeciesH2Repository speciesH2Repository, PokemonRepository pokemonRepository, SpeciesRepository speciesRepository,  DtoFunctionFactory factory) {
        this.pokemonH2Repository = pokemonH2Repository;
        this.speciesH2Repository = speciesH2Repository;
        this.pokemonRepository = pokemonRepository;
        this.speciesRepository = speciesRepository;
        this.factory = factory;
    }

    @Transactional
    public void create(Pokemon pokemon){
//        pokemonRepository.create(pokemon);
        pokemonH2Repository.create(pokemon);
    }

    public void create(PokemonCreateRequest pokemonRequest) {
        pokemonRequest.setSpeciesId(pokemonRequest.getSpeciesId());
        var species = speciesRepository.find(pokemonRequest.getSpeciesId())
                .orElseThrow(() -> new IllegalArgumentException("Pokemon species with id " + pokemonRequest.getSpeciesId() + " not found"));
        var pokemonToCreate = factory.pokemonCreateRequestToPokemon().apply(pokemonRequest);
        pokemonToCreate.setSpecies(species);
        pokemonRepository.create(pokemonToCreate);
    }

    public void create(UUID speciesId, PokemonCreateRequest pokemonRequest) {
        var species = speciesRepository.find(speciesId)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon species with id " + speciesId + " not found"));
        var pokemonToCreate = factory.pokemonCreateRequestToPokemon().apply(pokemonRequest);
        pokemonToCreate.setSpecies(species);
        pokemonRepository.create(pokemonToCreate);
    }

    public void update(PokemonEditRequest pokemonRequest) {
        pokemonRequest.setSpeciesId(pokemonRequest.getSpeciesId());
        var species = speciesRepository.find(pokemonRequest.getSpeciesId())
                .orElseThrow(() -> new IllegalArgumentException("Pokemon species with id " + pokemonRequest.getSpeciesId() + " not found"));
        var pokemonToUpdate = factory.pokemonEditRequestToPokemon().apply(pokemonRequest);
        pokemonToUpdate.setSpecies(species);
        pokemonRepository.update(pokemonToUpdate);
    }

    public void update(UUID speciesId, UUID pokemonId, PokemonEditRequest pokemonRequest) {
        pokemonRequest.setId(pokemonId);
        var species = speciesRepository.find(speciesId)
                .orElseThrow(() -> new NotFoundException("Pokemon species with id " + pokemonRequest.getSpeciesId() + " not found"));
        var pokemonToUpdate = factory.pokemonEditRequestToPokemon().apply(pokemonRequest);
        pokemonToUpdate.setSpecies(species);
        pokemonRepository.update(pokemonToUpdate);
    }

    public List<PokemonResponse> getPokemons() {
        return pokemonRepository.findAll()
                .stream().map(factory.pokemontoPokemonResponse())
                .collect(Collectors.toList());
    }

    public List<PokemonResponse> getPokemonsBySpeciesId(UUID speciesId) {
        System.out.println( pokemonRepository.findAllBySpeciesId(speciesId));
        return pokemonRepository.findAllBySpeciesId(speciesId)
                .stream().map(factory.pokemontoPokemonResponse())
                .collect(Collectors.toList());
    }

    public PokemonResponse findById(UUID id) {
        return pokemonRepository.find(id)
                .map(factory.pokemontoPokemonResponse())
                .orElseThrow(() -> new NotFoundException("Pokemon with id " + id + " not found"));
    }

    public Pokemon getPokemonById(UUID id) {
        return pokemonRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon with id " + id + " not found"));
    }

    public void delete(UUID id) {
        Pokemon pokemon = pokemonRepository.find(id)
                .orElseThrow(() -> new NotFoundException("Pokemon with id " + id + " not found"));
        pokemonRepository.delete(pokemon);
    }

}
