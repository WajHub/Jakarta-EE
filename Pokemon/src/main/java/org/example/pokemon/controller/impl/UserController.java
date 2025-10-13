package org.example.pokemon.controller.impl;

import org.example.pokemon.controller.api.IUserController;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.service.UserService;

import java.io.InputStream;
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
        return userService.getUserAvatar(uuid);
    }

    @Override
    public void putUserAvatar(UUID uuid, InputStream avatar){
        userService.putUserAvatar(uuid, avatar);
    }

    @Override
    public void deleteAvatar(UUID uuid) {
        userService.deleteAvatar(uuid);
    }
}
