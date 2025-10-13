package org.example.pokemon.repository.impl;

import org.example.pokemon.database.Datastore;
import org.example.pokemon.entity.User;
import org.example.pokemon.repository.api.IUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements IUserRepository {

    private final Datastore datastore;

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
