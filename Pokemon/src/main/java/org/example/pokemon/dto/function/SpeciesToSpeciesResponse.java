package org.example.pokemon.dto.function;

import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.PokemonSpeciesResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.PokemonSpecies;

import java.util.List;
import java.util.function.Function;

public class SpeciesToSpeciesResponse implements Function<PokemonSpecies, PokemonSpeciesResponse> {
    @Override
    public PokemonSpeciesResponse apply(PokemonSpecies species) {
        List<PokemonResponse> pokemons = species.getPokemons() == null
                ? List.of()
                : species.getPokemons().stream()
                .map(pokemon -> PokemonResponse.builder()
                        .id(pokemon.getId())
                        .name(pokemon.getName())
                        .level(pokemon.getLevel())
                        .build())
                .toList();

        return PokemonSpeciesResponse.builder()
                .id(species.getId())
                .name(species.getName())
                .increaseHealthPerLevel(species.getIncreaseHealthPerLevel())
                .increaseAttackPerLevel(species.getIncreaseAttackPerLevel())
                .increaseDefensePerLevel(species.getIncreaseDefensePerLevel())
                .pokemons(pokemons)
                .build();
    }
}
