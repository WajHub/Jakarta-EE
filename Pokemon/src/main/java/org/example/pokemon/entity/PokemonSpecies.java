package org.example.pokemon.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PokemonSpecies  implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;

    private int increaseHealthPerLevel;
    private int increaseAttackPerLevel;
    private int increaseDefensePerLevel;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PokemonSpecies evolutionTarget;
    private Integer levelToEvolve;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Pokemon> pokemons = new ArrayList<>();

    private PokemonType type;
}
