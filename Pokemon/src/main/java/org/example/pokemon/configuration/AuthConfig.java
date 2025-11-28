package org.example.pokemon.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Pokemon")
//@FormAuthenticationMechanismDefinition(
//        loginToContinue = @LoginToContinue(
//                loginPage = "/user/login.xhtml",
//                errorPage = "/user/error.xhtml"
//        )
//)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/pokemons",
        callerQuery = "select password from users where username = ?",
        groupsQuery = "select role from users__roles where id = (select id from users where username = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthConfig {
}
