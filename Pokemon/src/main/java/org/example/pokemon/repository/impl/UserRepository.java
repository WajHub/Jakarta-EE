package org.example.pokemon.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.database.Datastore;
import org.example.pokemon.entity.User;
import org.example.pokemon.repository.api.IUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class UserRepository implements IUserRepository {

    private Datastore datastore;

    @Inject
    public UserRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(datastore.findUser(id));
    }

    @Override
    public List<User> findAll() {
        return datastore.findAllUsers();
    }

    @Override
    public void create(User entity) {
        datastore.createUser(entity);
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void update(User entity) {

    }
}
