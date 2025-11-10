package org.example.pokemon.dto.function;

import org.example.pokemon.dto.request.PokemonEditRequest;
import org.example.pokemon.entity.Pokemon;

import java.time.LocalDate;
import java.util.function.Function;

public class PokemonEditRequestToPokemon implements Function<PokemonEditRequest, Pokemon> {
    @Override
    public Pokemon apply(PokemonEditRequest pokemonEditRequest) {
        return Pokemon.builder()
                .id(pokemonEditRequest.getId())
                .name(pokemonEditRequest.getName())
                .health(pokemonEditRequest.getHealth())
                .attack(pokemonEditRequest.getAttack())
                .defense(pokemonEditRequest.getDefense())
                .level(pokemonEditRequest.getLevel())
                .captureDate(pokemonEditRequest.getCaptureDate() == null ? LocalDate.now() : pokemonEditRequest.getCaptureDate())
                .build();
    }
}
