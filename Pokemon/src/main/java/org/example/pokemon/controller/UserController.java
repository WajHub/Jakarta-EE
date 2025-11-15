package org.example.pokemon.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.pokemon.dto.request.UserCreateRequest;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.User;
import org.example.pokemon.service.UserService;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @POST
    @Path("/register")
    public void create(UserCreateRequest userCreateRequest) {
        userService.create(userCreateRequest);
    }
}
