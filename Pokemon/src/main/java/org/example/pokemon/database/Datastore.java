package org.example.pokemon.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.entity.User;
import org.example.pokemon.utils.CloningUtility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class Datastore {

    private final CloningUtility cloningUtility;

    private final Set<User> users = new HashSet<>();

    private final Set<Pokemon> pokemons = new HashSet<>();

    private final Set<PokemonSpecies> pokemonSpecies = new HashSet<>();

    @Inject
    public Datastore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }


    public synchronized void createUser(User user) {
        if (users.stream().anyMatch(character -> character.getId().equals(user.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(user.getId()));
        }
        users.add(cloningUtility.clone(user));
    }

    public List<User> findAllUsers(){
        return users.stream()
                .map(cloningUtility::clone)
                .toList();
    }

    public User findUser(UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(cloningUtility::clone)
                .orElse(null);
    }

    public synchronized void createPokemonSpecies(PokemonSpecies pSpecies) {
        if (pokemonSpecies.stream().anyMatch(pokemon -> pokemon.getId().equals(pSpecies.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(pSpecies.getId()));
        }
        pokemonSpecies.add(cloningUtility.clone(pSpecies));
    }

    public synchronized void createPokemon(Pokemon pokemon) {
        if (pokemons.stream().anyMatch(p -> p.getId().equals(pokemon.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(pokemon.getId()));
        }
        pokemons.add(cloningUtility.clone(pokemon));
    }

    public List<Pokemon> findAllPokemons() {
        return pokemons.stream()
                .map(cloningUtility::clone)
                .toList();
    }

    public List<PokemonSpecies> findAllPokemonSpecies() {
        return pokemonSpecies.stream()
                .map(cloningUtility::clone)
                .toList();
    }

}
