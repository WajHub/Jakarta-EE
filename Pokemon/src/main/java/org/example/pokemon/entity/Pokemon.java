package org.example.pokemon.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "pokemons")
public class Pokemon implements Serializable {

    @Id
    private UUID id;
    private static final long serialVersionUID = 1L;

    private String name;
    private int level;

    private int health;
    private int attack;
    private int defense;

    private LocalDate captureDate;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private PokemonSpecies species;

    @ManyToOne
    @JoinColumn(name = "owner_username")
    private User owner;
}
