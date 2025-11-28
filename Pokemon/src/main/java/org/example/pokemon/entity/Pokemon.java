package org.example.pokemon.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Embedded
    CommonData commonData;

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


    @PrePersist
    public void onCreate() {
        if(commonData == null) {
            commonData = new CommonData();
        }
        commonData.setCreationDateTime(LocalDateTime.now());
        commonData.setLastModified(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate() {
        if (commonData != null) {
            commonData.setLastModified(LocalDateTime.now());
        }
    }

}
