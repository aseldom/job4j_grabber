package ru.job4j.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    public boolean checkFile(String file) {
        boolean res = true;
        if (!Files.isRegularFile(Paths.get(cachingDir, file))) {
            System.out.printf("Неверно введено название файла: %s%n%n", file);
            res = false;
        }
        return res;
    }

    @Override
    protected String load(String key) {
        String res = "";
        try (BufferedReader reader = new BufferedReader(
                new FileReader(Paths.get(cachingDir, key).toString()))) {
            res = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}