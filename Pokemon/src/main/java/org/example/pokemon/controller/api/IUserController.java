package org.example.pokemon.controller.api;

import org.example.pokemon.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserController {

    List<UserResponse> getUsers();

    UserResponse getUser(UUID uuid);
}
