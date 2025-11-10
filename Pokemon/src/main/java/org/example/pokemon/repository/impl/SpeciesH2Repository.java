package org.example.pokemon.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.repository.api.ISpeciesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class SpeciesH2Repository implements ISpeciesRepository  {

    @PersistenceContext(name = "pokemonsPu")
    private EntityManager em;

    @Override
    public Optional<PokemonSpecies> find(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<PokemonSpecies> findAll() {
        return List.of();
    }

    @Override
    public void create(PokemonSpecies entity) {
        em.persist(entity);
    }

    @Override
    public void delete(PokemonSpecies entity) {

    }

    @Override
    public void update(PokemonSpecies entity) {

    }
}
