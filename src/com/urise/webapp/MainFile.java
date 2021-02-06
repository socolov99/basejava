package com.urise.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "/Users/kirillsokolov/Desktop/course_basejava/basejava";
        File rootFile = new File(filePath);
        findFile(rootFile);

    }
    private static void findFile(File rootFile){
        for (File file  : Objects.requireNonNull(rootFile.listFiles())) {
            if (file.isDirectory()){
                findFile(file);
            }
            else {
                System.out.println(file.getName());
            }
        }
    }
}
