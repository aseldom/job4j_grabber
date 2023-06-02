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

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);

    public static void main(String[] args) throws IOException {
        int numberOfPages = 5;
        for (int i = 1; i <= numberOfPages; i++) {
            Connection connection = Jsoup.connect(String.format("%s%s", PAGE_LINK, i));
            Document document = connection.get();
            System.out.printf("------------ Page %s ------------\n", i);
            parsePage(document);
        }
    }

    public static void parsePage(Document document) {
        DateTimeParser dateTimeParser = new HabrCareerDateTimeParser();
        Elements rows = document.select(".vacancy-card__inner");
        rows.forEach(row -> {
            String dateElement = row.select(".vacancy-card__date")
                    .first()
                    .child(0)
                    .attr("datetime");
            LocalDateTime dateTime = dateTimeParser.parse(dateElement);
            Element titleElement = row.select(".vacancy-card__title").first();
            Element linkElement = titleElement.child(0);
            String vacancyName = titleElement.text();
            String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
            System.out.printf("%s --- %s %s%n", dateTime, vacancyName, link);
        });
    }
}