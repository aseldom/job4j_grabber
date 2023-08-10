package ru.job4j.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    public boolean checkFile(String file) {
        boolean res = true;
        if (!Files.isRegularFile(Path.of(cachingDir, file))) {
            System.out.printf("Неверно введено название файла: %s%n%n", file);
            res = false;
        }
        return res;
    }

    @Override
    protected String load(String key) {
        String res = "";
        try {
            res = Files.readString(Path.of(cachingDir, key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}