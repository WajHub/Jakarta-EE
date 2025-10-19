package org.example.pokemon.configuration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import org.example.pokemon.entity.User;
import org.example.pokemon.entity.UserRole;
import org.example.pokemon.service.UserService;

import java.util.List;
import java.util.UUID;

@WebListener
public class InitData implements ServletContextListener {

    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
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


    }
}
