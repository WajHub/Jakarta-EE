package org.example.pokemon.controller.api;

import org.example.pokemon.dto.response.UserResponse;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface IUserController {

    List<UserResponse> getUsers();

    UserResponse getUser(UUID uuid);

    byte[] getUserAvatar(UUID uuid);

    void postUserAvatar(UUID uuid, InputStream avatar);

    void putUserAvatar(UUID uuid, InputStream avatar);

    void deleteAvatar(UUID uuid);
}
