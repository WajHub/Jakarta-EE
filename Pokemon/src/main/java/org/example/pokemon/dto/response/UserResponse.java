package org.example.pokemon.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Builder
public record UserResponse (UUID uuid, String username, String email) {};
