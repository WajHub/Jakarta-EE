package org.example.pokemon.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.service.PokemonService;

import java.util.List;
import java.util.UUID;

@Path("/species/{speciesId}/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    @Inject
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PokemonResponse> getPokemons(@PathParam("speciesId") UUID speciesId) {
        return pokemonService.getPokemonsBySpeciesId(speciesId);
    }

    @GET
    @Path("/{pokemonId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PokemonResponse getPokemon(@PathParam("pokemonId") UUID id) {
        return pokemonService.findById(id);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPokemon(@PathParam("speciesId") UUID speciesId, PokemonCreateRequest pokemonRequest) {
        pokemonRequest.setId(UUID.randomUUID());
        pokemonService.create(speciesId, pokemonRequest);
    }

    @PATCH
    @Path("/{pokemonId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updatePokemon(@PathParam("pokemonId") UUID pokemonId, PokemonCreateRequest pokemonRequest) {
        // TODO: implement update logic
        return;
    }

    @DELETE
    @Path("/{pokemonId}")
    public void deletePokemon(@PathParam("pokemonId") UUID pokemonId) {
        pokemonService.delete(pokemonId);
    }

}
