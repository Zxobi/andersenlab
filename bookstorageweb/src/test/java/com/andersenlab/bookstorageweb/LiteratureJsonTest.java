package com.andersenlab.bookstorageweb;

import static org.assertj.core.api.Assertions.*;

import com.andersenlab.bookstorageweb.entity.Book;
import com.andersenlab.bookstorageweb.entity.Literature;
import com.andersenlab.bookstorageweb.entity.Magazine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
public class LiteratureJsonTest {

    private static final String BOOK_JSON = "{ " +
            "\"type\":\"book\", " +
            "\"title\":\"book title\", " +
            "\"author\":\"book author\" " +
            "}";

    private static final String MAGAZINE_JSON = "{ " +
            "\"type\":\"magazine\", " +
            "\"title\":\"magazine title\", " +
            "\"serialNumber\":100 " +
            "}";

    @Autowired
    private JacksonTester<Literature> json;

    @Test
    void testDeserializeBook() throws IOException {
        Literature literature = this.json.parseObject(BOOK_JSON);
        assertThat(literature).isInstanceOf(Book.class);
        Book book = (Book) literature;
        assertThat(book.getTitle()).isEqualTo("book title");
        assertThat(book.getAuthor()).isEqualTo("book author");
    }

    @Test
    void testDeserializeMagazine() throws IOException {
        Literature literature = this.json.parseObject(MAGAZINE_JSON);
        assertThat(literature).isInstanceOf(Magazine.class);
        Magazine magazine = (Magazine) literature;
        assertThat(magazine.getTitle()).isEqualTo("magazine title");
        assertThat(magazine.getSerialNumber()).isEqualTo(100);
    }

    @Test
    void testSerializeBook() throws IOException {
        Book book = new Book.Builder().setTitle("book title")
                .setAuthor("book author")
                .build();

        JsonContent<Literature> content = this.json.write(book);

        assertThat(content)
                .extractingJsonPathStringValue("@.title")
                .isEqualTo("book title");
        assertThat(content)
                .extractingJsonPathStringValue("@.author")
                .isEqualTo("book author");
    }

    @Test
    void testSerializeMagazine() throws IOException {
        Magazine magazine = new Magazine.Builder().setTitle("magazine title")
                .setSerialNumber(100)
                .build();

        JsonContent<Literature> content = this.json.write(magazine);

        assertThat(content)
                .extractingJsonPathStringValue("@.title")
                .isEqualTo("magazine title");
        assertThat(content)
                .extractingJsonPathNumberValue("@.serialNumber")
                .isEqualTo(100);
    }

}
