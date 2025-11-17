package org.example.pokemon.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.repository.api.IPokemonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return em.createQuery("select p from Pokemon p", Pokemon.class).getResultList();
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
        return em.createQuery("select p from Pokemon p where p.species.id = :speciesId", Pokemon.class)
                .setParameter("speciesId", speciesId)
                .getResultList();
    }

    public List<Pokemon> findAllBySpeciesIdAndUsername(UUID speciesId, String username) {
        return em.createQuery("select p from Pokemon p where p.species.id = :speciesId and p.owner.username = :username", Pokemon.class)
                .setParameter("speciesId", speciesId)
                .setParameter("username", username)
                .getResultList();
    }

}
