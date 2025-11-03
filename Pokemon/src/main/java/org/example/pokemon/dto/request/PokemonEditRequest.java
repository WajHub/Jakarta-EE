package org.example.pokemon.dto.request;


import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PokemonEditRequest {
    private UUID id;
    private String name;
    private int health;
    private int attack;
    private int defense;
    private int level;
    private LocalDate captureDate;
    private UUID speciesId;
}
