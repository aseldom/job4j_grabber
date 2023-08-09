package ru.job4j.cache.menu;

import ru.job4j.cache.DirFileCache;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Emulator {

    private static final int POINT_DIR = 1;
    private static final int LOAD_FILE_TO_CACHE = 2;
    private static final int GET_FILE = 3;
    private static final String EXIT = "Работа завершена";
    DirFileCache dirFileCache;

    final String menu = """
            Выберите пункт меню
            =================================================
            Введите 1, чтобы указать требуемую директорию
            Введите 2, чтобы Загрузить содержимое файла в кэш
            Введите 3, чтобы Получить содержимое файла из кэша
            Введите любое другое число для выхода.
            """;

    public void init(Scanner scanner) {
        String path = null;
        String file = null;
        boolean dirExist = false;
        boolean fileExist = false;
        boolean run = true;
        while (run) {
            System.out.println(menu);
            int in = Integer.parseInt(scanner.nextLine());
            switch (in) {
                case POINT_DIR -> {
                    System.out.println("Укажите кэшируемую директорию:");
                    path = scanner.nextLine();
                    dirExist = checkDir(path);
                    if (dirExist) {
                        dirFileCache = new DirFileCache(path);
                        fileExist = false;
                    }
                }
                case LOAD_FILE_TO_CACHE -> {
                    if (!dirExist) {
                        System.out.println("Сначала необходимо ввести валидную директорию\n");
                        break;
                    }
                    System.out.println("Укажите имя файла:");
                    file = scanner.nextLine();
                    fileExist = dirFileCache.checkFile(file);
                    if (!fileExist) {
                        break;
                    }
                    dirFileCache.get(file);
                }
                case GET_FILE -> {
                    if (!fileExist) {
                        System.out.println("Необходимо ввести валидную директорию и имя файла");
                        break;
                    }
                    System.out.println(dirFileCache.get(file));
                }
                default -> {
                    System.out.println(EXIT);
                    run = false;
                }
            }

        }
    }

    public boolean checkDir(String dir) {
        boolean res = true;
        if (!Files.isDirectory(Path.of(dir))) {
            System.out.printf("Введено неверное название директории: %s%n%n", dir);
            res = false;
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Emulator emulator = new Emulator();
        emulator.init(scanner);

    }
}
