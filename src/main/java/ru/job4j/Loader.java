package ru.job4j;

public class Loader {

    final static Runtime ENVIRONMENT = Runtime.getRuntime();

    public static void main(String[] args) throws ClassNotFoundException {
        Class loader = Loader.class;
        System.out.println("Класс переменной loader: " + loader);
        System.out.println("Загрузчик класса переменной loader:  " + loader.getClassLoader());
        System.out.println("FreeMemory =" + ENVIRONMENT.freeMemory() / 1000000);
        System.out.println("TotalMemory = " + ENVIRONMENT.totalMemory() / 1000000);
        System.out.println("MaxMemory = " + ENVIRONMENT.maxMemory() / 1000000);

    }

}