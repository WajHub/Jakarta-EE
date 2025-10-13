package org.example.pokemon;

import java.io.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.pokemon.controller.impl.UserController;

@WebServlet(urlPatterns = {"/api/*"})
@MultipartConfig(maxFileSize = 200 * 1024)
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
            if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            }
            if (path.matches(Patterns.USER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                byte[] portrait = userController.getUserAvatar(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (servletPath.equals("/api")) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.putUserAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (servletPath.equals("/api")) {
            if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.deleteAvatar(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    public static final class Patterns {

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern USERS = Pattern.compile("/users/?");
        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));
        public static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));

    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }
}