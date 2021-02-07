package com.urise.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {
        String filePath = "src/com/urise/webapp";
        File rootFile = new File(filePath);
        findFile(rootFile);
    }

    private static void findFile(File rootFile) {
        for (File file : Objects.requireNonNull(rootFile.listFiles())) {
            if (file.isDirectory()) {
                System.out.println("directory: "+file.getName());
                findFile(file);
            } else {
                System.out.println("file: "+file.getName());


            }
        }
    }
}
