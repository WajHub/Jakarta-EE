package org.example.pokemon.controller;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.pokemon.dto.request.UserCreateRequest;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.service.UserService;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @EJB
    private UserService userService;

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
