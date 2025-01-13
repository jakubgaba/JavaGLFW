package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShaderUtils {
    public static String loadShaderSource(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load shader at path: " + filePath, e);
        }
    }
}