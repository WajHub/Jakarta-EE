package org.example.pokemon.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.database.Datastore;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.User;
import org.example.pokemon.repository.api.IPokemonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class PokemonRepository implements IPokemonRepository {

    private Datastore datastore;

    @Inject
    public PokemonRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<Pokemon> find(UUID id) {
        return datastore.findPokemonById(id);
    }

    @Override
    public List<Pokemon> findAll() {
        return datastore.findAllPokemons();
    }

    @Override
    public void create(Pokemon entity) {
        datastore.createPokemon(entity);
    }

    @Override
    public void delete(Pokemon entity) {
        datastore.deletePokemon(entity);
    }

    @Override
    public void update(Pokemon entity) {

    }
}
