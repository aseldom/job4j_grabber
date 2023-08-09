package ru.job4j.pool;

public class IntegerPoolExample {
    public static void main(String[] args) {
        Integer a1 = 125;
        Integer a2 = new Integer(125);
        Integer a3 = new Integer(125);
        System.out.println(a3 == a2);
    }
}
