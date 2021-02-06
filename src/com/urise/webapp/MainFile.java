package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalFile());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        System.out.println(dir.isFile());
        for (String name : Objects.requireNonNull(dir.list())) {
            System.out.println(name);
        }

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            System.out.println(fileInputStream.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
