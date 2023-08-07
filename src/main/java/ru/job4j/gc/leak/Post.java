package ru.job4j.gc.leak;

import java.util.List;

public class Post {

    private int id;

    private String text;

    private List<Comment> comments;

    /* Change Integer to int */

    public Post(int id, String text, List<Comment> comments) {
        this.id = id;
        this.text = text;
        this.comments = comments;
    }

    public Post(String text, List<Comment> comments) {
        this.text = text;
        this.comments = comments;
    }
    /* Change Integer to int */

    public int getId() {
        return id;
    }
    /* Change Integer to int */

    public void setId(int id) {
        this.id = id;
    }

    /*остальные getter/setter*/

    /* Устаревшая и ненужная в данном случае конструкция
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
     */
}