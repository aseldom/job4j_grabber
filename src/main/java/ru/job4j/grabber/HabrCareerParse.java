package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);
    private final DateTimeParser dateTimeParser;
    private static final int NUMBER_OF_PAGES = 5;
    private List<Post> posts = new ArrayList<>();

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) {
        HabrCareerParse habrCareerParse = new HabrCareerParse(new HabrCareerDateTimeParser());
        habrCareerParse.posts = habrCareerParse.list(PAGE_LINK);
    }

    public List<Post> parsePage(Document document) {
        List<Post> postList = new ArrayList<>();
        Elements rows = document.select(".vacancy-card__inner");
        rows.forEach(row -> {
            String dateElement = row.select(".vacancy-card__date")
                    .first()
                    .child(0)
                    .attr("datetime");
            Element titleElement = row.select(".vacancy-card__title").first();
            Element linkElement = titleElement.child(0);
            String vacancyName = titleElement.text();
            String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
            String description = retrieveDescription(link);
            LocalDateTime dateTime = dateTimeParser.parse(dateElement);
            postList.add(new Post(vacancyName, link, description, dateTime));
        });
        return postList;
    }

    private String retrieveDescription(String link) {
        Connection connection = Jsoup.connect(link);
        String description = "";
        try {
            Document document = connection.get();
            description = document.select(".faded-content__container").first().text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return description;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> postList = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_PAGES; i++) {
            Connection connection = Jsoup.connect(String.format("%s%s", link, i));
            try {
                Document document = connection.get();
                postList.addAll(parsePage(document));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return postList;
    }
}