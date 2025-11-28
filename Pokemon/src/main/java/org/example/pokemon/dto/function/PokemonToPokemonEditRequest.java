package org.example.pokemon.dto.function;

import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.dto.request.PokemonEditRequest;
import org.example.pokemon.entity.Pokemon;

import java.util.function.Function;

public class PokemonToPokemonEditRequest implements Function<Pokemon, PokemonEditRequest>  {

    @Override
    public PokemonEditRequest apply(Pokemon pokemon) {
        return PokemonEditRequest.builder()
                .id(pokemon.getId())
                .name(pokemon.getName())
                .health(pokemon.getHealth())
                .attack(pokemon.getAttack())
                .defense(pokemon.getDefense())
                .level(pokemon.getLevel())
                .captureDate(pokemon.getCaptureDate())
                .speciesId(pokemon.getSpecies().getId())
                .version(pokemon.getCommonData().getVersion())
                .creationDateTime(pokemon.getCommonData().getCreationDateTime())
                .build();
    }
}
