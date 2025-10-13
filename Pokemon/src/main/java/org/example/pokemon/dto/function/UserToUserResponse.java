package org.example.pokemon.dto.function;

import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.User;

import java.util.function.Function;

public class UserToUserResponse implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return UserResponse.builder()
                .uuid(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
