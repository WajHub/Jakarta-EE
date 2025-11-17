package org.example.pokemon.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Pokemon")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/pokemons",
        callerQuery = "select password from users where login = ?",
        groupsQuery = "select role from users__roles where id = (select id from users where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthConfig {
}
