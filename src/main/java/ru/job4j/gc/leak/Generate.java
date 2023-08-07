package ru.job4j.gc.leak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public interface Generate  {

    void generate();

    default List<String> read(String path) {
        List<String> text = new ArrayList<>();
        try (var linesStream = Files.lines(Paths.get(path))) {
            linesStream.forEach(text::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}