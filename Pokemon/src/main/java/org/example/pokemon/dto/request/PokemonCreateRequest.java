package org.example.pokemon.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.*;
import org.example.pokemon.validation.PokemonRequestCreateGroup;
import org.example.pokemon.validation.ValidateLevel;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidateLevel(groups = { PokemonRequestCreateGroup.class, Default.class })
public class PokemonCreateRequest {
    private UUID id = UUID.randomUUID();

    @NotBlank
    private String name;

    @Min(0)
    private int health;
    @Min(0)
    private int attack;
    @Min(0)
    private int defense;

    private int level;
    private final LocalDate captureDate = LocalDate.now();
    private UUID speciesId;
}
