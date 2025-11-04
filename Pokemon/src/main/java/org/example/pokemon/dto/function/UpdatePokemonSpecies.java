package org.example.pokemon.dto.function;

import org.example.pokemon.dto.request.SpeciesEditRequest;
import org.example.pokemon.entity.PokemonSpecies;

import java.util.function.BiFunction;

public class UpdatePokemonSpecies implements BiFunction<PokemonSpecies, SpeciesEditRequest, PokemonSpecies> {
    @Override
    public PokemonSpecies apply(PokemonSpecies entity, SpeciesEditRequest request) {
        return PokemonSpecies.builder()
                .id(entity.getId())
                .name(request.getName() != null ? request.getName() : entity.getName())
                .increaseHealthPerLevel(request.getIncreaseHealthPerLevel())
                .increaseAttackPerLevel(request.getIncreaseAttackPerLevel())
                .increaseDefensePerLevel(request.getIncreaseDefensePerLevel())
                .evolutionTarget(request.getEvolutionTarget() != null ? request.getEvolutionTarget() : entity.getEvolutionTarget())
                .levelToEvolve(request.getLevelToEvolve() != null ? request.getLevelToEvolve() : entity.getLevelToEvolve())
                .pokemons(entity.getPokemons())
                .type(request.getType() != null ? request.getType() : entity.getType())
                .build();
    }
}
