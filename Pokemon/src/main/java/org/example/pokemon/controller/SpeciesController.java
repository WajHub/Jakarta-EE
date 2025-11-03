package org.example.pokemon.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.service.SpeciesService;

import java.util.List;
import java.util.UUID;

@Path("/species")
public class SpeciesController {

    private final SpeciesService speciesService;

    @Inject
    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpeciesResponse> getSpecies() {
        return speciesService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public SpeciesResponse getSpeciesById(@PathParam("id") UUID id) {
        return speciesService.findById(id);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createSpecies(PokemonSpecies pokemonSpecies) {
        speciesService.create(pokemonSpecies);
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateSpecies(@PathParam("id") UUID id, PokemonSpecies pokemonSpecies)
    {
        // TODO: implement update logic
        return;
    }

    @DELETE
    @Path("/{id}")
    public void deleteSpecies(@PathParam("id") UUID id) {
        speciesService.delete(id);
    }

}
