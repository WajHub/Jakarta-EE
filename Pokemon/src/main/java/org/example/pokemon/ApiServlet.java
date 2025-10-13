package org.example.pokemon;

import java.io.*;
import java.util.regex.Pattern;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.pokemon.controller.impl.UserController;

@WebServlet(urlPatterns = {"/api/*"})
public class ApiServlet extends HttpServlet {

    private final Jsonb jsonb = JsonbBuilder.create();

    private UserController userController;

    @Override
    public void init() throws ServletException {
        super.init();
        userController = (UserController) getServletContext().getAttribute("userController");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (servletPath.equals("/api")) {
            if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    public void destroy() {
    }

    public static final class Patterns {

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern USERS = Pattern.compile("/users/?");

    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }
}