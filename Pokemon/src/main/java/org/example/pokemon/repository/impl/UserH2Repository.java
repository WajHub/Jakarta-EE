package org.example.pokemon.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import org.example.pokemon.entity.User;
import org.example.pokemon.repository.api.IUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@NoArgsConstructor
@Dependent
public class UserH2Repository  implements IUserRepository {

    @PersistenceContext(name = "pokemonsPu")
    private EntityManager em;

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void update(User entity) {

    }
}
