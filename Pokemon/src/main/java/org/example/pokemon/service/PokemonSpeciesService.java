package org.example.pokemon.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.response.PokemonSpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.repository.impl.PokemonSpeciesRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class PokemonSpeciesService {

    private PokemonSpeciesRepository pokemonSpeciesRepository;
    private DtoFunctionFactory factory;

    @Inject
    public PokemonSpeciesService(PokemonSpeciesRepository pokemonSpeciesRepository, DtoFunctionFactory factory) {
        this.pokemonSpeciesRepository = pokemonSpeciesRepository;
        this.factory = factory;
    }

    public void create(PokemonSpecies pSpecies) {
        pokemonSpeciesRepository.create(pSpecies);
    }

    public List<PokemonSpeciesResponse> findAll() {
        return pokemonSpeciesRepository.findAll()
                .stream()
                .map(factory.speciesToSpeciesResponse())
                .collect(Collectors.toList());
    }
}
