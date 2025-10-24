package org.example.pokemon.dto.function;

import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.entity.Pokemon;

import java.util.function.Function;

public class PokemonCreateRequestToPokemon implements Function<PokemonCreateRequest, Pokemon> {
    @Override
    public Pokemon apply(PokemonCreateRequest pokemonCreateRequest) {
        return Pokemon.builder()
                .id(pokemonCreateRequest.getId())
                .name(pokemonCreateRequest.getName())
                .health(pokemonCreateRequest.getHealth())
                .attack(pokemonCreateRequest.getAttack())
                .defense(pokemonCreateRequest.getDefense())
                .level(pokemonCreateRequest.getLevel())
                .captureDate(pokemonCreateRequest.getCaptureDate())
                .build();
    }
}
