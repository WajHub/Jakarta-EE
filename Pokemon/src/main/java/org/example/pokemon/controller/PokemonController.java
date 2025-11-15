package org.example.pokemon.controller;

import jakarta.ejb.EJB;
import jakarta.annotation.security.RolesAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.request.PokemonEditRequest;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.service.PokemonService;

import java.util.List;
import java.util.UUID;

@Path("/species/{speciesId}/pokemons")
public class PokemonController {

    @EJB
    private PokemonService pokemonService;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"USER", "ADMIN"})
    public List<PokemonResponse> getPokemons(@PathParam("speciesId") UUID speciesId) {
        return pokemonService.getPokemonsBySpeciesId(speciesId);
    }


    @GET
    @Path("/{pokemonId}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"USER", "ADMIN"})
    public PokemonResponse getPokemon(@PathParam("pokemonId") UUID id) {
        return pokemonService.findById(id);
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"USER", "ADMIN"})
    public void createPokemon(@PathParam("speciesId") UUID speciesId, PokemonCreateRequest pokemonRequest) {
        pokemonRequest.setId(UUID.randomUUID());
        pokemonService.create(speciesId, pokemonRequest);
    }

    @PATCH
    @Path("/{pokemonId}")
    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"USER", "ADMIN"})
    public void updatePokemon(
            @PathParam("speciesId") UUID speciesId,
            @PathParam("pokemonId") UUID pokemonId,
            PokemonEditRequest pokemonRequest) {
        pokemonService.update(speciesId, pokemonId, pokemonRequest);
    }


    @DELETE
    @Path("/{pokemonId}")
    @RolesAllowed({"USER", "ADMIN"})
    public void deletePokemon(@PathParam("pokemonId") UUID pokemonId) {
        pokemonService.delete(pokemonId);
    }
}
