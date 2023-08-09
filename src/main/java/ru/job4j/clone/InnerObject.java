package ru.job4j.clone;

import java.util.HashMap;
import java.util.Map;

public class InnerObject implements Cloneable {
    int num;

    @Override
    protected InnerObject clone() throws CloneNotSupportedException {
        return (InnerObject) super.clone();
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        System.out.println(map.get("2"));
    }
}