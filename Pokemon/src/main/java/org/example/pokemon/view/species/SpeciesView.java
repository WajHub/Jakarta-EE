package org.example.pokemon.view.species;

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
import org.example.pokemon.service.SpeciesService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class SpeciesView  implements Serializable {
    private static final long serialVersionUID = 1L;

    private SpeciesService service;
    private PokemonService pokemonService;
    @Setter
    @Getter
    private SpeciesResponse pokemonSpecies;

    @Setter
    @Getter
    private List<PokemonResponse> pokemons;

    @Setter
    @Getter
    private UUID id;

    @Inject
    public SpeciesView(SpeciesService service, PokemonService pokemonService) {
        this.pokemonService = pokemonService;
        this.service = service;
    }

    public void init() throws IOException {
        try {
            this.pokemonSpecies = service.findById(id);
            this.pokemons = pokemonService.getPokemonsBySpeciesId(pokemonSpecies.getId());
            System.out.println(pokemons);
        } catch (NotFoundException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pokemon species not found");
            facesContext.responseComplete();
        }
    }

    public void deleteAction(PokemonResponse pokemon) {
        pokemonService.delete(pokemon.getId());
        this.pokemons = pokemonService.getPokemonsBySpeciesId(this.id);
    }

}
