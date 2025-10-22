package org.example.pokemon.dto.response;

import lombok.Builder;
import org.example.pokemon.entity.PokemonSpecies;

import java.time.LocalDate;

@Builder
public record PokemonResponse (String name, LocalDate captureDate, PokemonSpecies species) {
}
