package br.booksapi.books.google;

import br.booksapi.books.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Streams.stream;
import static java.util.Collections.emptyList;

public class BooksDeserializer {

    public List<Book> deserializeList(String source) {
        try {
            final JsonNode items = new ObjectMapper().readTree(source).withArray("items");

            if(items.isArray()){
                return stream(items.elements()).map(this::deserialize).collect(Collectors.toList());
            }
            return emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book deserializeSingle(String source) {
        try {
            return this.deserialize(new ObjectMapper().readTree(source));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Book deserialize(final JsonNode bookNode) {
        return new Book(
                bookNode.get("id").asText(),
                bookNode.get("volumeInfo").get("title").asText()
        );
    }
}
