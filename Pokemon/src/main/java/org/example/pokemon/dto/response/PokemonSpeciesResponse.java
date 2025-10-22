package org.example.pokemon.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonSpeciesResponse {
    private UUID id;
    private String name;
    private int increaseHealthPerLevel;
    private int increaseAttackPerLevel;
    private int increaseDefensePerLevel;
}