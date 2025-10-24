package org.example.pokemon.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.repository.impl.PokemonRepository;
import org.example.pokemon.repository.impl.SpeciesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class PokemonService {

    private PokemonRepository pokemonRepository;
    private SpeciesRepository speciesRepository;
    private DtoFunctionFactory factory;

    @Inject
    public PokemonService(PokemonRepository pokemonRepository, SpeciesRepository speciesRepository,  DtoFunctionFactory factory) {
        this.pokemonRepository = pokemonRepository;
        this.speciesRepository = speciesRepository;
        this.factory = factory;
    }

    public void create(Pokemon pokemon) {
        pokemonRepository.create(pokemon);
    }

    public void create(PokemonCreateRequest pokemonRequest) {
        pokemonRequest.setSpeciesId(pokemonRequest.getSpeciesId());
        PokemonSpecies species = speciesRepository.find(pokemonRequest.getSpeciesId())
                .orElseThrow(() -> new IllegalArgumentException("Pokemon species with id " + pokemonRequest.getSpeciesId() + " not found"));
        Pokemon pokemonToCreate = factory.pokemonCreateRequestToPokemon().apply(pokemonRequest);
        pokemonToCreate.setSpecies(species);
        this.create(pokemonToCreate);
    }

    public List<PokemonResponse> getPokemons() {
        return pokemonRepository.findAll()
                .stream().map(factory.pokemontoPokemonResponse())
                .collect(Collectors.toList());
    }

    public PokemonResponse findById(UUID id) {
        return pokemonRepository.find(id)
                .map(factory.pokemontoPokemonResponse())
                .orElseThrow(() -> new IllegalArgumentException("Pokemon with id " + id + " not found"));
    }

    public void delete(UUID id) {
        Pokemon pokemon = pokemonRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon with id " + id + " not found"));
        System.out.println(pokemon);
        pokemonRepository.delete(pokemon);
    }

}
