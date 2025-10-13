package org.example.pokemon.controller.impl;

import org.example.pokemon.controller.api.IUserController;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    public UserResponse getUser(UUID uuid) {
        return userService.getUser(uuid);
    }

    @Override
    public byte[] getUserAvatar(UUID uuid) {
        System.out.println(Arrays.toString(userService.getUserAvatar(uuid)));
        return userService.getUserAvatar(uuid);
    }
}
