package org.example.pokemon.service;

import jakarta.annotation.security.PermitAll;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.request.UserCreateRequest;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.User;
import org.example.pokemon.entity.UserRole;
import org.example.pokemon.exception.HttpRequestException;
import org.example.pokemon.exception.NotFoundException;
import org.example.pokemon.repository.impl.UserH2Repository;
import org.example.pokemon.repository.impl.UserRepository;
import org.example.pokemon.utils.AvatarUtility;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@LocalBean
@Stateless
@NoArgsConstructor
public class UserService {

    private DtoFunctionFactory factory;
    private UserRepository userRepository;
    private UserH2Repository userH2Repository;
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    public UserService(UserRepository userRepository, UserH2Repository userH2Repository, Pbkdf2PasswordHash passwordHash, DtoFunctionFactory factory) {
        this.factory = factory;
        this.userRepository = userRepository;
        this.userH2Repository = userH2Repository;
        this.passwordHash = passwordHash;
    }

    @PermitAll
    public void create(User user) {
        if(userH2Repository.find(user.getId()).isEmpty()) {
            user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
            userH2Repository.create(user);
        }
    }

    @PermitAll
    public void create(UserCreateRequest user) {
        if(userH2Repository.findByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
            User newUser = factory.userCreateRequestToUser().apply(user);
            newUser.setId(UUID.randomUUID());
            newUser.setRoles(List.of(UserRole.ROLE_USER));
            userH2Repository.create(newUser);
        }
    }

    public List<UserResponse> getUsers() {
        return userH2Repository.findAll()
                .stream().map(factory.usertoUserResponse())
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> getUserById(UUID id) {
        return  userRepository.find(id)
                .map(factory.usertoUserResponse());
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

    @PermitAll
    public boolean verify(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }
}
