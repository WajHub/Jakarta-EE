package org.example.pokemon.dto.function;

import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.User;

import java.util.function.Function;

public class PokemonToPokemonResponse  implements Function<Pokemon, PokemonResponse> {

    @Override
    public PokemonResponse apply(Pokemon pokemon) {
        return PokemonResponse.builder()
                .name(pokemon.getName())
                .health(pokemon.getHealth())
                .attack(pokemon.getAttack())
                .defense(pokemon.getDefense())
                .speciesName(pokemon.getSpecies().getName())
                .captureDate(pokemon.getCaptureDate().toString())
                .build();
    }
}
