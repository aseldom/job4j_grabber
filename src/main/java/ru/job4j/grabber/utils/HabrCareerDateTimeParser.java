package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HabrCareerDateTimeParser implements DateTimeParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public LocalDateTime parse(String parse) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\w\\d{2}:\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(parse);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("DateTime string \"%s\" is not valid", parse));
        }
        return LocalDateTime.parse(matcher.group(), FORMATTER);
    }
}