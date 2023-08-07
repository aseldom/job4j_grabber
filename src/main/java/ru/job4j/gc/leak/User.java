package ru.job4j.gc.leak;

public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* Устаревшая и ненужная в данном случае конструкция
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
     */
}