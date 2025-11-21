package org.example.pokemon.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.pokemon.entity.User;
import org.example.pokemon.entity.UserRole;
import org.example.pokemon.repository.impl.UserH2Repository;
import org.example.pokemon.repository.impl.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@NoArgsConstructor(force = true)
public class InitAdmin {

    private final UserH2Repository userRepository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitAdmin(UserH2Repository userRepository, Pbkdf2PasswordHash passwordHash) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userRepository.find(UUID.fromString("aaf8b3ad-f935-4e77-a01e-718f338a37ca")).isEmpty()) {
            String hashedPassword = passwordHash.generate("admin".toCharArray());
            User admin = User.builder()
                    .id(UUID.fromString("aaf8b3ad-f935-4e77-a01e-718f338a37ca"))
                    .username("admin")
                    .password(hashedPassword)
                    .email("admin@gmail.com")
                    .roles(List.of(UserRole.ROLE_ADMIN, UserRole.ROLE_USER))
                    .build();
            userRepository.create(admin);
        }
    }


}
