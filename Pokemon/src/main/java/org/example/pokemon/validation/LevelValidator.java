package org.example.pokemon.validation;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.pokemon.dto.request.PokemonCreateRequest;
import org.example.pokemon.repository.impl.SpeciesH2Repository;

@Dependent
public class LevelValidator implements ConstraintValidator<ValidateLevel, PokemonCreateRequest> {

    @Inject
    private SpeciesH2Repository speciesH2Repository;
    @Override
    public void initialize(ValidateLevel constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PokemonCreateRequest pokemonCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("TEST!");
        var species = speciesH2Repository.find(pokemonCreateRequest.getSpeciesId());
        System.out.println("Validating level for species: " + species);
        if (species.isEmpty()) {
            return true;
        }
        var speciesBeforeEvolve = speciesH2Repository.findByEvolutionTarget(pokemonCreateRequest.getSpeciesId());
        System.out.println("Species before evolve: " + speciesBeforeEvolve);
        if(speciesBeforeEvolve.isEmpty() || speciesBeforeEvolve.get().getLevelToEvolve() == null) {
            return true;
        }
        System.out.println("Level to evolve: " + speciesBeforeEvolve.get().getLevelToEvolve() + ", Pokemon level: " + pokemonCreateRequest.getLevel());
        return speciesBeforeEvolve.get().getLevelToEvolve() <= pokemonCreateRequest.getLevel();
    }
}