package org.example.pokemon.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "pokemon_species")
public class PokemonSpecies  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String name;

    private int increaseHealthPerLevel;
    private int increaseAttackPerLevel;
    private int increaseDefensePerLevel;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "evolution_target_id")
    private PokemonSpecies evolutionTarget;

    private Integer levelToEvolve;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "species")
    private List<Pokemon> pokemons = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PokemonType type;
}
