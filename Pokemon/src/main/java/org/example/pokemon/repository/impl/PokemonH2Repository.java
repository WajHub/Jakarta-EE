package org.example.pokemon.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.request.PokemonFilter;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.Pokemon_;
import org.example.pokemon.repository.api.IPokemonRepository;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.*;

@NoArgsConstructor
@Dependent
public class PokemonH2Repository implements IPokemonRepository  {

    @PersistenceContext(name = "pokemonsPu")
    private EntityManager em;

    @Override
    public Optional<Pokemon> find(UUID id) {
        return Optional.ofNullable(em.find(Pokemon.class, id));
    }

    @Override
    public List<Pokemon> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokemon> cq = cb.createQuery(Pokemon.class);
        Root<Pokemon> root = cq.from(Pokemon.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public void create(Pokemon entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Pokemon entity) {
        em.remove(em.find(Pokemon.class, entity.getId()));
    }

    @Override
    public void update(Pokemon entity) {
        em.merge(entity);
    }

    public List<Pokemon> findAllBySpeciesId(UUID speciesId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokemon> cq = cb.createQuery(Pokemon.class);
        Root<Pokemon> root = cq.from(Pokemon.class);
        cq.select(root).where(cb.equal(root.get("species").get("id"), speciesId));
        return em.createQuery(cq).getResultList();
    }

    public List<Pokemon> findAllFiltered(UUID speciesId, PokemonFilter pokemonFilter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokemon> cq = cb.createQuery(Pokemon.class);
        Root<Pokemon> root = cq.from(Pokemon.class);

        List<Predicate> predicates = new ArrayList<>();

        if (pokemonFilter.getLevel() != null) {
            System.out.println("Filtering by level: " + pokemonFilter.getLevel());
            predicates.add(cb.ge(root.get(Pokemon_.level), pokemonFilter.getLevel()));
        }

        if(pokemonFilter.getName() != null && !pokemonFilter.getName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get(Pokemon_.name)), "%" + pokemonFilter.getName().toLowerCase() + "%"));
        }

        predicates.add(cb.equal(root.get("species").get("id"), speciesId));
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(cq).getResultList();
    }

    public List<Pokemon> findAllBySpeciesIdAndUsername(UUID speciesId, String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pokemon> cq = cb.createQuery(Pokemon.class);
        Root<Pokemon> root = cq.from(Pokemon.class);
        cq.select(root).where(cb.and(
                cb.equal(root.get("species").get("id"), speciesId),
                cb.equal(root.get("owner").get("username"), username)
        ));
        return em.createQuery(cq).getResultList();
    }
}
