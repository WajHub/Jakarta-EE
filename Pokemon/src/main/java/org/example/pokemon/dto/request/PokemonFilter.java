package org.example.pokemon.dto.request;

import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PokemonFilter {
    @QueryParam("name")
    private String name;

    @QueryParam("level")
    private Integer level;
}
