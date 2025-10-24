package org.example.pokemon.view.species;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.service.SpeciesService;

import java.util.List;

@RequestScoped
@Named
public class SpeciesList {

    private SpeciesService service;
    private List<SpeciesResponse> speciesList;

    @Inject
    public SpeciesList(SpeciesService service) {
        this.service = service;
    }

    public List<SpeciesResponse> getSpeciesList() {
        if (speciesList == null) {
            speciesList = service.findAll();
            return speciesList;
        } else {
            return speciesList;
        }
    }

    public void deleteAction(SpeciesResponse species) {
        service.delete(species.getId());
        speciesList = null;
    }
}
