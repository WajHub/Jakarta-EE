package org.example.pokemon.dto.function;

import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.PokemonSpeciesResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.PokemonSpecies;

import java.util.function.Function;

public class SpeciesToSpeciesResponse implements Function<PokemonSpecies, PokemonSpeciesResponse> {
    @Override
    public PokemonSpeciesResponse apply(PokemonSpecies species) {
        return PokemonSpeciesResponse.builder()
                .id(species.getId())
                .name(species.getName())
                .increaseHealthPerLevel(species.getIncreaseHealthPerLevel())
                .increaseAttackPerLevel(species.getIncreaseAttackPerLevel())
                .increaseDefensePerLevel(species.getIncreaseDefensePerLevel())
                .build();
    }
}
