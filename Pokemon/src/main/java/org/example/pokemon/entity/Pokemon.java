package org.example.pokemon.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Pokemon implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private int level;

    private int health;
    private int attack;
    private int defense;

    private LocalDate captureDate;

    private PokemonSpecies species;
    private User owner;
}
