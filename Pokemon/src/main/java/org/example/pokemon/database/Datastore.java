package org.example.pokemon.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.entity.User;
import org.example.pokemon.utils.CloningUtility;

import java.util.*;

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
        pokemons.add(cloningUtility.clone(pokemon));
        pokemonSpecies.stream()
                .filter(species -> species.getId().equals(pokemon.getSpecies().getId()))
                .findFirst()
                .ifPresent(species -> species.getPokemons().add(cloningUtility.clone(pokemon)));
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

    public Optional<PokemonSpecies> findPokemonSpeciesById(UUID id) {
        return pokemonSpecies.stream()
                .filter(species -> species.getId().equals(id))
                .findFirst()
                .map(cloningUtility::clone);
    }

    public void deletePokemonSpecies(PokemonSpecies entity) {
        pokemonSpecies.removeIf(species -> species.getId().equals(entity.getId()));
    }

    public void deletePokemon(Pokemon entity) {
        var id = entity.getId();
        pokemons.removeIf(p -> p.getId().equals(id));
        removePokemonFromSpecies(id);
    }

    private void removePokemonFromSpecies(UUID pokemonId) {
        pokemonSpecies.forEach(s -> s.getPokemons().removeIf(p -> p.getId().equals(pokemonId)));
    }

    public Optional<Pokemon> findPokemonById(UUID id) {
        return pokemons.stream()
                .filter(pokemon -> pokemon.getId().equals(id))
                .findFirst()
                .map(cloningUtility::clone);
    }

    public void updatePokemon(Pokemon entity) {
        deletePokemon(entity);
        createPokemon(entity);
    }
}
