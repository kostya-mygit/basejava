package ru.javaops.basejava;

import java.io.*;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javaops/basejava");

        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }
        System.out.println();

        printAllFilesInDirectory(dir);

        //before java 7
        FileInputStream fis1 = null;
        try {
            fis1 = new FileInputStream(filePath);
            fis1.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis1 != null) {
                try {
                    fis1.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //since java 7
        try (FileInputStream fis2 = new FileInputStream(filePath)) {
            fis2.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printAllFilesInDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    printAllFilesInDirectory(f);
                } else {
                    System.out.println(f.getName());
                }
            }
        }
    }
}
