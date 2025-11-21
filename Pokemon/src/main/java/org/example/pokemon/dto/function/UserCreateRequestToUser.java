package org.example.pokemon.dto.function;

import org.example.pokemon.dto.request.SpeciesEditRequest;
import org.example.pokemon.dto.request.UserCreateRequest;
import org.example.pokemon.dto.response.SpeciesResponse;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.entity.User;

import java.util.function.BiFunction;
import java.util.function.Function;

public class UserCreateRequestToUser implements Function<UserCreateRequest, User>  {
    @Override
    public User apply(UserCreateRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
