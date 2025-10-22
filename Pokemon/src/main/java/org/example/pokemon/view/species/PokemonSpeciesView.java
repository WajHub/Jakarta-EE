package org.example.pokemon.view.species;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.pokemon.dto.response.PokemonSpeciesResponse;
import org.example.pokemon.exception.NotFoundException;
import org.example.pokemon.service.PokemonSpeciesService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named
public class PokemonSpeciesView {
    private PokemonSpeciesService service;
    @Setter
    @Getter
    private PokemonSpeciesResponse pokemonSpecies;
    @Setter
    @Getter
    private UUID id;

    @Inject
    public PokemonSpeciesView(PokemonSpeciesService service) {
        this.service = service;
    }

    public void init() throws IOException {
        try {
            this.pokemonSpecies = service.findById(id);
        } catch (NotFoundException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pokemon species not found");
            facesContext.responseComplete();
        }
    }
}
