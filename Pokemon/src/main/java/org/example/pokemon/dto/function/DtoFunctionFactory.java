package org.example.pokemon.dto.function;

import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.User;

public class DtoFunctionFactory {

    public UserToUserResponse usertoUserResponse() { return new UserToUserResponse();};
}
