package org.example.pokemon.configuration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.pokemon.database.Datastore;
import org.example.pokemon.utils.CloningUtility;

@WebListener
public class CreateDatasource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("datastore", new Datastore(new CloningUtility()));
    }

}
