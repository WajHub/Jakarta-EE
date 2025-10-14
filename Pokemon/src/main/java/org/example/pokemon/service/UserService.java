package org.example.pokemon.service;

import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.User;
import org.example.pokemon.exception.HttpRequestException;
import org.example.pokemon.exception.NotFoundException;
import org.example.pokemon.repository.impl.UserRepository;
import org.example.pokemon.utils.AvatarUtility;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {

    private final DtoFunctionFactory factory;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, DtoFunctionFactory factory) {
        this.factory = factory;
        this.userRepository = userRepository;
    }

    public void create(User user) {
        userRepository.create(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream().map(factory.usertoUserResponse())
                .collect(Collectors.toList());
    }

    public UserResponse getUser(UUID uuid) {
        return userRepository.find(uuid)
                .map(factory.usertoUserResponse())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User not found! %s", uuid.toString()
                        ))
                );
    }

    public byte[] getUserAvatar(UUID uuid) {
        return userRepository.find(uuid)
            .map((user) ->{
               if(AvatarUtility.avatarExists(uuid.toString())) {
                   return AvatarUtility.loadFile(uuid.toString());
               }
               else{
                   throw new NotFoundException("Avatar not found!");
               }
            })
            .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    public void postUserAvatar(UUID uuid, InputStream avatar) {
        userRepository.find(uuid)
            .ifPresentOrElse(
                (user) ->{
                    if(AvatarUtility.avatarExists(uuid.toString())) {
                        throw new HttpRequestException("User has already avatar!", 400);
                    }
                    else {
                        try {
                            AvatarUtility.saveFile(uuid.toString(), avatar.readAllBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, () ->  {
                    throw new NotFoundException("User not found!");
                });
    }

    public void putUserAvatar(UUID uuid, InputStream avatar) {
        userRepository.find(uuid)
            .ifPresentOrElse(
            (user) ->{
                try {
                    AvatarUtility.deleteFile(uuid.toString());
                    AvatarUtility.saveFile(uuid.toString(), avatar.readAllBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, () ->  {
                throw new NotFoundException("User not found!");
            });
    }

    public void deleteAvatar(UUID uuid) {
        userRepository.find(uuid)
            .ifPresentOrElse(
            (user) ->{
                if(!AvatarUtility.deleteFile(uuid.toString())) {
                    throw new HttpRequestException("User doesn't have avatar!", 400);
                }
            }, () ->  {
                throw new NotFoundException("User not found!");
            });
    }
}
