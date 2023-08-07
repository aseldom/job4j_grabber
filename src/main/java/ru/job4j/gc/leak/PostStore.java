package ru.job4j.gc.leak;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PostStore {

    /* Убираю статик */

    private Map<Integer, Post> posts = new HashMap<>();

    /* Т.к. приложение не многопоточное, то AtomicInteger не нужен */

    private Integer id = 1;

    public Post add(Post post) {
        post.setId(id);
        posts.put(id, post);
        id++;
        return post;
    }

    public void removeAll() {
        posts.clear();
    }

    public Collection<Post> getPosts() {
        return posts.values();
    }
}