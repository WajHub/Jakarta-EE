package org.example.pokemon.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.exception.NotFoundException;
import org.example.pokemon.repository.impl.PokemonSpeciesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class SpeciesService {

    private PokemonSpeciesRepository pokemonSpeciesRepository;
    private DtoFunctionFactory factory;

    @Inject
    public SpeciesService(PokemonSpeciesRepository pokemonSpeciesRepository, DtoFunctionFactory factory) {
        this.pokemonSpeciesRepository = pokemonSpeciesRepository;
        this.factory = factory;
    }

    public void create(PokemonSpecies pSpecies) {
        pokemonSpeciesRepository.create(pSpecies);
    }

    public List<SpeciesResponse> findAll() {
        return pokemonSpeciesRepository.findAll()
                .stream()
                .map(factory.speciesToSpeciesResponse())
                .collect(Collectors.toList());
    }

    public SpeciesResponse findById(UUID id) {
        Optional<PokemonSpecies> species = pokemonSpeciesRepository.find(id);
        return species
                .map(factory.speciesToSpeciesResponse())
                .orElseThrow(() -> new NotFoundException("Pokemon species not found"));
    }

    public void delete(UUID id) {
        pokemonSpeciesRepository.delete(
                pokemonSpeciesRepository
                    .find(id)
                    .orElseThrow(() -> new NotFoundException("Pokemon species not found"))
        );
    }
}
