package org.example.pokemon.utils;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

@ApplicationScoped
@NoArgsConstructor
public class AvatarUtility {

    public static String PATH_TO_RESOURCES = "~/";

    public static byte[] loadFile(String fileName) {
        try {
            Path path = Paths.get(PATH_TO_RESOURCES+fileName+".png");
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
            Path filePath = Paths.get(PATH_TO_RESOURCES+fileName+".png");
            File avatarFile = filePath.toFile();
            avatarFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(avatarFile);
            fos.write(avatar);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e.getCause());
        }
    }

    public static boolean avatarExists(String fileName) {
        try {
            Path filePath = Paths.get(PATH_TO_RESOURCES+fileName+".png");
            System.out.println(filePath);
            return Files.exists(filePath);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(PATH_TO_RESOURCES+fileName+".png");

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file: " + fileName, e);
        }
    }

}
