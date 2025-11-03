package org.example.pokemon.dto.function;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.entity.Pokemon;

import java.util.function.Function;

@ApplicationScoped
public class DtoFunctionFactory {

    public UserToUserResponse usertoUserResponse() { return new UserToUserResponse();}

    public PokemonToPokemonResponse pokemontoPokemonResponse() { return new PokemonToPokemonResponse();};
    public SpeciesToSpeciesResponse speciesToSpeciesResponse() { return new SpeciesToSpeciesResponse();};

    public PokemonCreateRequestToPokemon pokemonCreateRequestToPokemon() {return new PokemonCreateRequestToPokemon(); }

    public PokemonToPokemonEditRequest  pokemonToPokemonEditRequest() {return new PokemonToPokemonEditRequest();}
    public PokemonEditRequestToPokemon  pokemonEditRequestToPokemon() {return new PokemonEditRequestToPokemon();}
}
