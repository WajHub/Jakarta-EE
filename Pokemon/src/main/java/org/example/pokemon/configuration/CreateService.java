package org.example.pokemon.configuration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.pokemon.database.Datastore;
import org.example.pokemon.dto.function.DtoFunctionFactory;
import org.example.pokemon.repository.impl.UserRepository;
import org.example.pokemon.service.UserService;

@WebListener
public class CreateService implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        Datastore dataSource = (Datastore) event.getServletContext().getAttribute("datastore");

        UserRepository userRepository = new UserRepository(dataSource);

        event.getServletContext().setAttribute("userService", new UserService(userRepository, new DtoFunctionFactory()));
    }
}
