package org.example.pokemon.view.pokemon;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.TransactionalException;
import lombok.Getter;
import lombok.Setter;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.request.PokemonEditRequest;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.service.PokemonService;
import org.example.pokemon.service.SpeciesService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class PokemonUpdateView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final FacesContext facesContext;

    private PokemonService pokemonService;
    private SpeciesService speciesService;
    private DtoFunctionFactory factory;

    @Setter
    @Getter
    private PokemonEditRequest pokemonEditRequest;

    @Getter
    @Setter
    private PokemonResponse collisionOriginalPokemon;

    @Getter
    @Setter
    private List<SpeciesResponse> speciesList;

    @Getter
    @Setter
    private UUID id;

    @Inject
    public PokemonUpdateView(PokemonService pokemonService, SpeciesService speciesService, DtoFunctionFactory factory, FacesContext facesContext) {
        this.pokemonService = pokemonService;
        this.speciesService = speciesService;
        this.factory = factory;
        this.facesContext = facesContext;
    }

    public void init() {
        if (pokemonEditRequest == null) {
            var existingPokemon = pokemonService.getPokemonById(id);
            pokemonEditRequest  = factory.pokemonToPokemonEditRequest().apply(existingPokemon);
        }
        if (speciesList == null) {
            speciesList = speciesService.findAll();
        }
    }


    public String saveAction() {
        try {
            pokemonService.update(pokemonEditRequest);
            return "/species/species_list.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof OptimisticLockException) {
                    init();
                    collisionOriginalPokemon = factory.pokemontoPokemonResponse().apply(
                            pokemonService.getPokemonById(pokemonEditRequest.getId())
                    );
                    pokemonEditRequest.setVersion(collisionOriginalPokemon.getVersion());
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Version collision.", null));
                    return null;
                }
                cause = cause.getCause();
            }
            throw ex;
        }
    }
}
