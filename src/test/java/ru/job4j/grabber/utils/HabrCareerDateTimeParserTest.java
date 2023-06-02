package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HabrCareerDateTimeParserTest {

    @Test
    void parseDateTimeMatchingPattern() {
        HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
        String in = "2023-06-01T09:23:20";
        String expect = "2023-06-01T09:23:20";
        assertThat(parser.parse(in).toString()).isEqualTo(expect);
    }

    @Test
    void parseInvalidDateTimeFormatThenException() {
        HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
        String in = "2023x06-01T09:23:20+03:00";
        String expect = "2023-06-01T09:23:20";
        assertThatThrownBy(() -> parser.parse(in))
                .isInstanceOf(DateTimeParseException.class);
    }
}