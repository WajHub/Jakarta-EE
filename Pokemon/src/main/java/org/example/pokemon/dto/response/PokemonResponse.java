package org.example.pokemon.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import org.example.pokemon.entity.PokemonSpecies;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PokemonResponse {
    private final UUID id;
    private final String name;
    private int health;
    private int attack;
    private int defense;
    private final int level;
    private final LocalDate captureDate;
    private final String speciesName;
    private final String ownerUsername;
}