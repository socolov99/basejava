package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./";
        File dir = new File(filePath);
        printDirectoryDeeply(dir, "");
    }

    public static void printDirectoryDeeply(File dir, String offset) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(offset + "file: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(offset + "dir: " + file.getName());
                    printDirectoryDeeply(file, offset + "\t");
                }
            }
        }
    }
}