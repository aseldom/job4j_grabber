package ru.job4j.pool;

public class StringPoolExample {
    public static void main(String[] args) {
        String string1 = "Hello";
        String string2 = new String("Hello");
        String string3 = string2.intern();
        System.out.println(string1 == string3);
    }
}