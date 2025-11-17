package org.example.pokemon.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.pokemon.dto.request.SpeciesEditRequest;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.entity.UserRole;
import org.example.pokemon.service.SpeciesService;

import java.util.List;
import java.util.UUID;

@Path("/species")
public class SpeciesController {

    @EJB
    private SpeciesService speciesService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ROLE_USER, UserRole.ROLE_ADMIN})
    public List<SpeciesResponse> getSpecies() {
        return speciesService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ROLE_USER, UserRole.ROLE_ADMIN})
    public SpeciesResponse getSpeciesById(@PathParam("id") UUID id) {
        return speciesService.findById(id);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ROLE_ADMIN})
    public void createSpecies(PokemonSpecies pokemonSpecies) {
        pokemonSpecies.setId(UUID.randomUUID());
        speciesService.create(pokemonSpecies);
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ROLE_ADMIN})
    public void updateSpecies(@PathParam("id") UUID id, SpeciesEditRequest pokemonSpecies) {
        speciesService.update(id, pokemonSpecies);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({UserRole.ROLE_ADMIN})
    public void deleteSpecies(@PathParam("id") UUID id) {
        speciesService.delete(id);
    }
}
