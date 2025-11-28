package org.example.pokemon.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.repository.api.ISpeciesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Dependent
public class SpeciesH2Repository implements ISpeciesRepository  {

    @PersistenceContext(name = "pokemonsPu")
    private EntityManager em;

    @Override
    public Optional<PokemonSpecies> find(UUID id) {
        return Optional.ofNullable(em.find(PokemonSpecies.class, id));
    }

    public Optional<PokemonSpecies> findByEvolutionTarget(UUID evolutionTargetId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PokemonSpecies> query = builder.createQuery(PokemonSpecies.class);
        Root<PokemonSpecies> root = query.from(PokemonSpecies.class);
        query.select(root).where(builder.equal(root.get("evolutionTarget").get("id"), evolutionTargetId));
        List<PokemonSpecies> results = em.createQuery(query).getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public List<PokemonSpecies> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<PokemonSpecies> query = builder.createQuery(PokemonSpecies.class);
        Root<PokemonSpecies> root = query.from(PokemonSpecies.class);
        query.select(root);
        return em.createQuery(query).getResultList();
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
