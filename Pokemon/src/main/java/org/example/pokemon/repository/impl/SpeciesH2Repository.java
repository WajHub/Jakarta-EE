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
        return Optional.ofNullable(em.find(PokemonSpecies.class, id));
    }

    @Override
    public List<PokemonSpecies> findAll() {
        return em.createQuery("SELECT s FROM PokemonSpecies s", PokemonSpecies.class).getResultList();
    }

    @Override
    public void create(PokemonSpecies entity) {
        em.persist(entity);
    }

    @Override
    public void delete(PokemonSpecies entity) {
        if (entity == null || entity.getId() == null) {
            return;
        }
        PokemonSpecies managed = em.find(PokemonSpecies.class, entity.getId());
        if (managed != null) {
            em.remove(managed);
        }
    }

    @Override
    public void update(PokemonSpecies entity) {
        if (entity == null) {
            return;
        }
        em.merge(entity);
    }
}
