package org.example.pokemon.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.repository.impl.PokemonSpeciesRepository;

@ApplicationScoped
@NoArgsConstructor
public class PokemonSpeciesService {

    private PokemonSpeciesRepository pokemonSpeciesRepository;

    @Inject
    public PokemonSpeciesService(PokemonSpeciesRepository pokemonSpeciesRepository) {
        this.pokemonSpeciesRepository = pokemonSpeciesRepository;
    }

    public void create(PokemonSpecies pSpecies) {
        pokemonSpeciesRepository.create(pSpecies);
    }
}
