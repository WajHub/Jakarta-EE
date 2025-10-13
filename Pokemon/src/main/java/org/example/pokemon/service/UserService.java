package org.example.pokemon.service;

import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.User;
import org.example.pokemon.repository.impl.UserRepository;

import java.util.List;
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
}
