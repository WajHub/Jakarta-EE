package org.example.pokemon.dto.function;

import org.example.pokemon.dto.response.PokemonResponse;
import org.example.pokemon.dto.response.UserResponse;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.User;

import java.util.function.Function;

public class PokemonToPokemonResponse  implements Function<Pokemon, PokemonResponse> {

    @Override
    public PokemonResponse apply(Pokemon pokemon) {
        return PokemonResponse.builder()
                .id(pokemon.getId())
                .name(pokemon.getName())
                .level(pokemon.getLevel())
                .health(pokemon.getHealth())
                .attack(pokemon.getAttack())
                .defense(pokemon.getDefense())
                .speciesName(pokemon.getSpecies().getName())
                .captureDate(pokemon.getCaptureDate().toString())
                .ownerUsername(pokemon.getOwner() != null ? pokemon.getOwner().getUsername() : "N/A")
                .version(pokemon.getCommonData().getVersion())
                .createdDateTime(pokemon.getCommonData().getCreationDateTime())
                .lastModified(pokemon.getCommonData().getLastModified())
                .build();
    }
}
