package org.example.pokemon.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.pokemon.controller.api.IUserController;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.service.UserService;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor
public class UserController implements IUserController {

    private UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public void postUserAvatar(UUID uuid, InputStream avatar) {
        userService.postUserAvatar(uuid, avatar);
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
