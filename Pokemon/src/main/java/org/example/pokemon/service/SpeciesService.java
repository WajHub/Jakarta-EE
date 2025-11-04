package org.example.pokemon.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.exception.NotFoundException;
import org.example.pokemon.repository.impl.SpeciesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class SpeciesService {

    private SpeciesRepository pokemonSpeciesRepository;
    private DtoFunctionFactory factory;

    @Inject
    public SpeciesService(SpeciesRepository pokemonSpeciesRepository, DtoFunctionFactory factory) {
        this.pokemonSpeciesRepository = pokemonSpeciesRepository;
        this.factory = factory;
    }

    public void create(PokemonSpecies pSpecies) {
        pokemonSpeciesRepository.create(pSpecies);
    }

    public void update(UUID id, PokemonSpecies pSpecies) {
        pokemonSpeciesRepository.find(id).ifPresent(pokemonSpecies -> {
            if(pSpecies.getName() == null) pSpecies.setName(pokemonSpecies.getName());
            if(pSpecies.getType() == null) pSpecies.setType(pokemonSpecies.getType());
            if(pSpecies.getEvolutionTarget() == null) pSpecies.setEvolutionTarget(pokemonSpecies.getEvolutionTarget());
            if(pSpecies.getPokemons() == null) pSpecies.setPokemons(pokemonSpecies.getPokemons());
            if(pSpecies.getIncreaseAttackPerLevel()== 0) pSpecies.setIncreaseAttackPerLevel(pokemonSpecies.getIncreaseAttackPerLevel());
            if(pSpecies.getIncreaseDefensePerLevel() == 0) pSpecies.setIncreaseDefensePerLevel(pokemonSpecies.getIncreaseDefensePerLevel());
            if(pSpecies.getIncreaseHealthPerLevel() == 0) pSpecies.setIncreaseHealthPerLevel(pokemonSpecies.getIncreaseHealthPerLevel());
            pSpecies.setId(pokemonSpecies.getId());
        });
        pokemonSpeciesRepository.update(pSpecies);
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
