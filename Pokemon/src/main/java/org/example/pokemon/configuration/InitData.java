package org.example.pokemon.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.example.pokemon.entity.Pokemon;
import org.example.pokemon.entity.PokemonSpecies;
import org.example.pokemon.entity.User;
import org.example.pokemon.entity.UserRole;
import org.example.pokemon.service.PokemonService;
import org.example.pokemon.service.SpeciesService;
import org.example.pokemon.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
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
                .increaseAttackPerLevel(10)
                .increaseDefensePerLevel(5)
                .increaseHealthPerLevel(100)
                .levelToEvolve(12)
                .pokemons(new ArrayList<>())
                .build();

        PokemonSpecies raichuSpecies = PokemonSpecies.builder()
                .id(UUID.fromString("a70c86ce-bf99-4335-b2af-3d6535958973"))
                .name("Raichu")
                .increaseAttackPerLevel(20)
                .increaseDefensePerLevel(10)
                .increaseHealthPerLevel(200)
                .levelToEvolve(null)
                .pokemons(new ArrayList<>())
                .build();

        PokemonSpecies charmanderSpecies = PokemonSpecies.builder()
                .id(UUID.fromString("6614a2ee-96b8-4cae-ae18-61691d05834e"))
                .name("Chahrmander")
                .increaseAttackPerLevel(25)
                .increaseDefensePerLevel(15)
                .increaseHealthPerLevel(120)
                .levelToEvolve(null)
                .pokemons(new ArrayList<>())
                .build();
        pikachuSpecies.setEvolutionTarget(raichuSpecies);

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

        pokemonService.create(pokemon_huberta);
        pokemonService.create(pokemon_admina);
    }
}
