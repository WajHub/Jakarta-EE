package org.example.pokemon.view.species;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.pokemon.dto.response.PokemonSpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.service.PokemonSpeciesService;

import java.util.List;

@RequestScoped
@Named
public class PokemonSpeciesList {

    private PokemonSpeciesService service;
    private List<PokemonSpeciesResponse> speciesList;

    @Inject
    public PokemonSpeciesList(PokemonSpeciesService service) {
        this.service = service;
    }

    public List<PokemonSpeciesResponse> getSpeciesList() {
        if (speciesList == null) {
            speciesList = service.findAll();
            System.out.println(speciesList);
            return speciesList;
        } else {
            return speciesList;
        }
    }
}
