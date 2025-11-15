package org.example.pokemon.configuration;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.pokemon.entity.*;
import org.example.pokemon.service.PokemonService;
import org.example.pokemon.service.SpeciesService;
import org.example.pokemon.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
public class InitData {

    private UserService userService;
    private PokemonService pokemonService;
    private SpeciesService pokemonSpeciesService;

    @Inject
    public InitData(UserService userService,  PokemonService pokemonService, SpeciesService pokemonSpeciesService) {
        this.userService = userService;
        this.pokemonService = pokemonService;
        this.pokemonSpeciesService = pokemonSpeciesService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        User admin = User.builder()
                .id(UUID.fromString("aaf8b3ad-f935-4e77-a01e-718f338a37ca"))
                .username("admin")
                .password("password_admin")
                .email("admin@gmail.com")
                .roles(List.of(UserRole.ADMIN, UserRole.USER))
                .build();
        User hubert = User.builder()
                .id(UUID.fromString("9f1b99da-ffe8-48ab-ba14-68d0e113d5de"))
                .username("hubert")
                .password("password")
                .email("hubert@gmail.com")
                .roles(List.of(UserRole.USER))
                .build();
        User user = User.builder()
                .id(UUID.fromString("5b030d40-b529-4150-a88e-094a24f08dc8"))
                .username("user")
                .password("password_user")
                .email("user@gmail.com")
                .roles(List.of(UserRole.USER))
                .build();
        User test = User.builder()
                .id(UUID.fromString("fea12d2a-d65a-49ce-9a3e-d6d5b31e0878"))
                .username("test")
                .password("password_test")
                .email("test@gmail.com")
                .roles(List.of(UserRole.USER))
                .build();
        List<User>initUsers = List.of(admin, hubert, test, user);
        initUsers.forEach(u -> userService.create(u));

        PokemonSpecies pikachuSpecies = PokemonSpecies.builder()
                .id(UUID.fromString("965830df-f75b-40dd-8200-1cb0a1be4d38"))
                .name("Pikachu")
                .increaseAttackPerLevel(6)
                .increaseDefensePerLevel(5)
                .increaseHealthPerLevel(50)
                .levelToEvolve(12)
                .pokemons(new ArrayList<>())
                .type(PokemonType.ELECTRIC)
                .build();

        PokemonSpecies raichuSpecies = PokemonSpecies.builder()
                .id(UUID.fromString("a70c86ce-bf99-4335-b2af-3d6535958973"))
                .name("Raichu")
                .increaseAttackPerLevel(8)
                .increaseDefensePerLevel(6)
                .increaseHealthPerLevel(60)
                .levelToEvolve(null)
                .pokemons(new ArrayList<>())
                .type(PokemonType.ELECTRIC)
                .build();
        pikachuSpecies.setEvolutionTarget(raichuSpecies);

        PokemonSpecies charmanderSpecies = PokemonSpecies.builder()
                .id(UUID.fromString("6614a2ee-96b8-4cae-ae18-61691d05834e"))
                .name("Charmander")
                .increaseAttackPerLevel(10)
                .increaseDefensePerLevel(3)
                .increaseHealthPerLevel(45)
                .levelToEvolve(16)
                .pokemons(new ArrayList<>())
                .type(PokemonType.FIRE)
                .build();
        PokemonSpecies charmeleonSpecies = PokemonSpecies.builder()
                .id(UUID.fromString("d3f5e2b4-E29b-4c1c-9f7a-8f4e5c2b6a1f"))
                .name("Charmeleon")
                .increaseAttackPerLevel(12)
                .increaseDefensePerLevel(5)
                .increaseHealthPerLevel(55)
                .levelToEvolve(36)
                .pokemons(new ArrayList<>())
                .type(PokemonType.FIRE)
                .build();
        charmanderSpecies.setEvolutionTarget(charmeleonSpecies);
        PokemonSpecies charizardSpecies = PokemonSpecies.builder()
                .id(UUID.fromString("f7c3e1d2-3b6a-4f8e-9c2d-1e2f3a4b5c6d"))
                .name("Charizard")
                .increaseAttackPerLevel(15)
                .increaseDefensePerLevel(8)
                .increaseHealthPerLevel(70)
                .levelToEvolve(null)
                .pokemons(new ArrayList<>())
                .type(PokemonType.FIRE)
                .build();
        charmeleonSpecies.setEvolutionTarget(charizardSpecies);


        Pokemon pokemon_huberta = Pokemon.builder()
                .id(UUID.fromString("70c51310-63e6-464c-ba13-5a7464cd311d"))
                .species(pikachuSpecies)
                .name("Moj_pokemon")
                .level(11)
                .health(200)
                .attack(12)
                .defense(14)
                .owner(hubert)
                .captureDate(LocalDate.now())
                .build();

        Pokemon pokemon_admina = Pokemon.builder()
                .id(UUID.fromString("3a47a2ca-a927-4a78-be0c-b40ad0987658"))
                .species(pikachuSpecies)
                .name("Nazwa")
                .level(11)
                .health(200)
                .attack(12)
                .defense(14)
                .owner(admin)
                .captureDate(LocalDate.now())
                .build();

        pokemonSpeciesService.create(pikachuSpecies);
        pokemonSpeciesService.create(raichuSpecies);
        pokemonSpeciesService.create(charmanderSpecies);
        pokemonSpeciesService.create(charmeleonSpecies);
        pokemonSpeciesService.create(charizardSpecies);

        pokemonService.create(pokemon_huberta);
        pokemonService.create(pokemon_admina);
    }
}
