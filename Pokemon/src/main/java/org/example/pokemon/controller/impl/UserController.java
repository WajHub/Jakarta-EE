package org.example.pokemon.controller.impl;

import org.example.pokemon.controller.api.IUserController;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.service.UserService;

import java.util.List;

public class UserController implements IUserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @Override
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }


    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

}
