package org.example.pokemon.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class AvatarUtility {

    private static final String PATH_TO_RESOURCES = "/src/main/resources/avatars/";

    public static byte[] loadFile(String fileName) {
        try {
            Path rootDit = Paths.get(System.getProperty("user.dir"));
            while (rootDit != null && !rootDit.getFileName().toString().equalsIgnoreCase("Pokemon")) {
                rootDit = rootDit.getParent();
            }
            Path path = Paths.get(rootDit+PATH_TO_RESOURCES+fileName+".png");
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("Not found: " + path);
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e.getCause());
        }
    }

    public static void saveFile(String fileName, byte[] avatar) {
        try {
            Path rootDir = Paths.get(System.getProperty("user.dir"));
            while (rootDir != null && !rootDir.getFileName().toString().equalsIgnoreCase("Pokemon")) {
                rootDir = rootDir.getParent();
            }

            Path avatarDir = Paths.get(rootDir + PATH_TO_RESOURCES);
            Path filePath = avatarDir.resolve(fileName + ".png");

            if (!Files.exists(avatarDir)) {
                Files.createDirectories(avatarDir);
            }
            Files.write(filePath, avatar);
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e.getCause());
        }
    }

    public static boolean avatarExists(String fileName) {
        try {
            Path rootDir = Paths.get(System.getProperty("user.dir"));
            while (rootDir != null && !rootDir.getFileName().toString().equalsIgnoreCase("Pokemon")) {
                rootDir = rootDir.getParent();
            }

            Path path = Paths.get(rootDir + PATH_TO_RESOURCES + fileName + ".png");
            return Files.exists(path);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteFile(String fileName) {
        try {
            Path rootDir = Paths.get(System.getProperty("user.dir"));
            while (rootDir != null && !rootDir.getFileName().toString().equalsIgnoreCase("Pokemon")) {
                rootDir = rootDir.getParent();
            }

            Path path = Paths.get(rootDir + PATH_TO_RESOURCES + fileName + ".png");

            if (Files.exists(path)) {
                Files.delete(path);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file: " + fileName, e);
        }
    }

}
