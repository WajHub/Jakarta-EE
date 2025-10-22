package org.example.pokemon.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.pokemon.database.Datastore;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.entity.User;
import org.example.pokemon.repository.api.IPokemonSpeciesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor
public class PokemonSpeciesRepository implements IPokemonSpeciesRepository {

    private Datastore datastore;

    @Inject
    public PokemonSpeciesRepository(Datastore datastore) {
        this.datastore = datastore;
    }


    @Override
    public Optional<PokemonSpecies> find(UUID id) {
        return datastore.findPokemonSpeciesById(id);
    }

    @Override
    public List<PokemonSpecies> findAll() {
        return datastore.findAllPokemonSpecies();
    }

    @Override
    public void create(PokemonSpecies entity) {
        datastore.createPokemonSpecies(entity);
    }

    @Override
    public void delete(PokemonSpecies entity) {
        datastore.deletePokemonSpecies(entity);
    }

    @Override
    public void update(PokemonSpecies entity) {

    }
}
