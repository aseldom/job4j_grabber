package ru.job4j.gc;

import java.util.Random;

public class GCTypeDemo {

    final static Runtime ENVIRONMENT = Runtime.getRuntime();

    private static void info() {
        System.out.print("FreeMemory =" + ENVIRONMENT.freeMemory() / 1000000 + " -- ");
        System.out.print("TotalMemory = " + ENVIRONMENT.totalMemory() / 1000000 + " -- ");
        System.out.println("MaxMemory = " + ENVIRONMENT.maxMemory() / 1000000);
    }

    public static void main(String[] args) {
        Random random = new Random();
        int length = 100;
        long freeM = 0;
        String[] data = new String[1_000_000];
        for (int i = 0; ; i = (i + 1) % data.length) {
            data[i] = String.valueOf((char) random.nextInt(255)).repeat(length);
            if (freeM != ENVIRONMENT.freeMemory() / 1000000) {
                freeM = ENVIRONMENT.freeMemory() / 1000000;
                System.out.print("i = " + i + " -- ");
                info();
            }
        }
    }
}