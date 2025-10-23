package org.example.pokemon.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.repository.impl.PokemonRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class PokemonService {

    private PokemonRepository pokemonRepository;
    private DtoFunctionFactory factory;

    @Inject
    public PokemonService(PokemonRepository pokemonRepository,  DtoFunctionFactory factory) {
        this.pokemonRepository = pokemonRepository;
        this.factory = factory;
    }

    public void create(Pokemon pokemon) {
        pokemonRepository.create(pokemon);
    }

    public List<PokemonResponse> getPokemons() {
        return pokemonRepository.findAll()
                .stream().map(factory.pokemontoPokemonResponse())
                .collect(Collectors.toList());
    }

    public void delete(UUID id) {
        System.out.println("TEST");
        Pokemon pokemon = pokemonRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon with id " + id + " not found"));
        System.out.println(pokemon);
        pokemonRepository.delete(pokemon);
    }

}
