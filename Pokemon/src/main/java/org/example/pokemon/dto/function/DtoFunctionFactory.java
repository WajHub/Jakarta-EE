package org.example.pokemon.dto.function;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DtoFunctionFactory {

    public UserToUserResponse usertoUserResponse() { return new UserToUserResponse();};
}
