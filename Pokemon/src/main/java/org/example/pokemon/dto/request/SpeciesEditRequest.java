package org.example.pokemon.dto.request;

import lombok.*;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.entity.PokemonType;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpeciesEditRequest {
    private UUID id;
    private String name;

    private int increaseHealthPerLevel;
    private int increaseAttackPerLevel;
    private int increaseDefensePerLevel;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PokemonSpecies evolutionTarget;
    private Integer levelToEvolve;

    private PokemonType type;
}
