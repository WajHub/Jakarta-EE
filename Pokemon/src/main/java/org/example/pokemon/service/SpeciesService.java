package org.example.pokemon.service;


import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.request.SpeciesEditRequest;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.repository.impl.SpeciesH2Repository;
import org.example.pokemon.repository.impl.SpeciesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@LocalBean
@Stateless
@NoArgsConstructor
public class SpeciesService {

    private SpeciesH2Repository speciesH2Repository;
    private DtoFunctionFactory factory;

    @Inject
    public SpeciesService(SpeciesH2Repository speciesH2Repository, DtoFunctionFactory factory) {
        this.speciesH2Repository = speciesH2Repository;
        this.factory = factory;
    }


    public void create(PokemonSpecies pSpecies) {
        if(speciesH2Repository.find(pSpecies.getId()).isEmpty())
            speciesH2Repository.create(pSpecies);
    }


    public void update(UUID id, SpeciesEditRequest pSpecies) {
        speciesH2Repository.find(id).ifPresent(entity -> {
            var updatedSpecies = factory.updatePokemonSpecies().apply(entity, pSpecies);
            speciesH2Repository.update(updatedSpecies);
        });

    }

    public List<SpeciesResponse> findAll() {
        return speciesH2Repository.findAll()
                .stream()
                .map(factory.speciesToSpeciesResponse())
                .collect(Collectors.toList());
    }

    public SpeciesResponse findById(UUID id) {
        Optional<PokemonSpecies> species = speciesH2Repository.find(id);
        return species
                .map(factory.speciesToSpeciesResponse())
                .orElseThrow(() -> new NotFoundException("Pokemon species not found"));
    }


    public void delete(UUID id) {
        speciesH2Repository.delete(
                speciesH2Repository
                    .find(id)
                    .orElseThrow(() -> new NotFoundException("Pokemon species not found"))
        );
    }
}
