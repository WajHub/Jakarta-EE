package org.example.pokemon.view.pokemon;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.request.PokemonEditRequest;
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

    private PokemonService pokemonService;
    private SpeciesService speciesService;
    private DtoFunctionFactory factory;

    @Setter
    @Getter
    private PokemonEditRequest pokemonEditRequest;

    @Getter
    @Setter
    private List<SpeciesResponse> speciesList;

    @Getter
    @Setter
    private UUID id;

    @Inject
    public PokemonUpdateView(PokemonService pokemonService, SpeciesService speciesService, DtoFunctionFactory factory) {
        this.pokemonService = pokemonService;
        this.speciesService = speciesService;
        this.factory = factory;
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

    public String cancelAction() {
        return "/species/species_list.xhtml?faces-redirect=true";
    }


    public String saveAction() {
        pokemonService.update(pokemonEditRequest);
        return "/species/species_list.xhtml?faces-redirect=true";
    }
}
