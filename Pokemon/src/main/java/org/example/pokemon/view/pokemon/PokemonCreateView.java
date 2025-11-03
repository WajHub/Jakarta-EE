package org.example.pokemon.view.pokemon;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.service.PokemonService;
import org.example.pokemon.service.SpeciesService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class PokemonCreateView  implements Serializable {
    private static final long serialVersionUID = 1L;

    private PokemonService pokemonService;
    private SpeciesService speciesService;

    @Setter
    @Getter
    private PokemonCreateRequest pokemonCreateRequest;

    @Getter
    @Setter
    private List<SpeciesResponse> speciesList;

    @Inject
    public PokemonCreateView(PokemonService pokemonService, SpeciesService speciesService) {
        this.pokemonService = pokemonService;
        this.speciesService = speciesService;
    }

    public void init() {
        if (pokemonCreateRequest == null) {
            pokemonCreateRequest = PokemonCreateRequest.builder()
                    .id(UUID.randomUUID())
                    .build();
        }
        if (speciesList == null) {
            speciesList = speciesService.findAll();
        }
    }

    public String cancelAction() {
        return "/species/species_list.xhtml?faces-redirect=true";
    }


    public String saveAction() {
        pokemonService.create(pokemonCreateRequest);
        return "/species/species_list.xhtml?faces-redirect=true";
    }
}
