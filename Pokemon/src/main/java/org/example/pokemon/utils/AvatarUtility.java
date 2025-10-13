package org.example.pokemon.utils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class AvatarUtility {

    private static final String PATH_TO_RESOURCES = "/src/main/resources/avatars/";

    public static byte[] loadFromFileSystem(String fileName) {
        try {
            Path rootDit = Paths.get(System.getProperty("user.dir"));
            while (rootDit != null && !rootDit.getFileName().toString().equalsIgnoreCase("Pokemon")) {
                rootDit = rootDit.getParent();
            }
            Path path = Paths.get(rootDit+PATH_TO_RESOURCES+fileName);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("Not found: " + path);
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e.getCause());
        }
    }
}
