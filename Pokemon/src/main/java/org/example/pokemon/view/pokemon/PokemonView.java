package org.example.pokemon.view.pokemon;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.exception.NotFoundException;
import org.example.pokemon.service.PokemonService;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class PokemonView implements Serializable {
    private static final long serialVersionUID = 1L;
    private PokemonService pokemonService;

    @Setter
    @Getter
    private PokemonResponse pokemonResponse;
    @Setter
    @Getter
    private UUID id;

    @Inject
    public PokemonView(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
    public void init() throws IOException{
        try {
            this.pokemonResponse = pokemonService.findById(id);
        } catch (NotFoundException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pokemon not found");
            facesContext.responseComplete();
        }

    }
}
