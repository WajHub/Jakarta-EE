package org.example.pokemon.repository.api;

import org.example.pokemon.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends IRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
