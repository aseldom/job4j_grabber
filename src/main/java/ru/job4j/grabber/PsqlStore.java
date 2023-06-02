package ru.job4j.grabber;

import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public static void main(String[] args) throws Exception {
        List<Post> postList;
        Properties properties = new Properties();
        HabrCareerParse parse = new HabrCareerParse(new HabrCareerDateTimeParser());
        try (InputStream in = new FileInputStream("db/liquibase_test.properties")) {
            properties.load(in);
        }
        try (PsqlStore store = new PsqlStore(properties)) {
            postList = new ArrayList<>(parse.list("https://career.habr.com/vacancies/java_developer?page="));
            postList.forEach(store::save);
            System.out.println("------------------ Print all posts ------------------");
            store.getAll().forEach(post -> {
                System.out.println(post);
                System.out.println("-".repeat(50));
            });
            System.out.println("------------------ Find by id (15) ------------------");
            System.out.println(store.findById(15));
        }
    }

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement st = cnn.prepareStatement(
                    "INSERT INTO posts (name, text, link, created) VALUES (?, ?, ?, ?)"
                            + "ON CONFLICT (link) DO NOTHING")) {
            st.setString(1, post.getTitle());
            st.setString(2, post.getDescription());
            st.setString(3, post.getLink());
            st.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement st = cnn.prepareStatement("SELECT * FROM posts")) {
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    posts.add(new Post(
                            res.getInt("id"),
                            res.getString("name"),
                            res.getString("text"),
                            res.getString("link"),
                            res.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement st = cnn.prepareStatement("SELECT * FROM posts WHERE id = ?")) {
            st.setInt(1, id);
            try (ResultSet res = st.executeQuery()) {
                if (res.next()) {
                    post = new Post(
                            res.getInt("id"),
                            res.getString("name"),
                            res.getString("text"),
                            res.getString("link"),
                            res.getTimestamp("created").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }
}