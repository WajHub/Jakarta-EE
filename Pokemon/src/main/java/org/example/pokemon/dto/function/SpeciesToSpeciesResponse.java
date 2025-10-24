package org.example.pokemon.dto.function;

import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;

import java.util.List;
import java.util.function.Function;

public class SpeciesToSpeciesResponse implements Function<PokemonSpecies, SpeciesResponse> {
    @Override
    public SpeciesResponse apply(PokemonSpecies species) {
        List<PokemonResponse> pokemons = species.getPokemons() == null
                ? List.of()
                : species.getPokemons().stream()
                .map(pokemon -> PokemonResponse.builder()
                        .id(pokemon.getId())
                        .name(pokemon.getName())
                        .level(pokemon.getLevel())
                        .build())
                .toList();

        return SpeciesResponse.builder()
                .id(species.getId())
                .name(species.getName())
                .increaseHealthPerLevel(species.getIncreaseHealthPerLevel())
                .increaseAttackPerLevel(species.getIncreaseAttackPerLevel())
                .increaseDefensePerLevel(species.getIncreaseDefensePerLevel())
                .pokemons(pokemons)
                .type(species.getType() != null ? species.getType().name() : null)
                .levelToEvolve(species.getLevelToEvolve() != null ? species.getLevelToEvolve() : 0)
                .evolutionTargetName(species.getEvolutionTarget() != null ? species.getEvolutionTarget().getName() : null)
                .build();
    }
}
