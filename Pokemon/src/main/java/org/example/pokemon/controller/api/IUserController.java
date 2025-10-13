package org.example.pokemon.controller.api;

import org.example.pokemon.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface IUserController {

    List<UserResponse> getUsers();

    UserResponse getUserById(Long id);
}
