package br.booksapi.books;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "book")
public class Book {

    @Id
    private final String id;

    private final String name;

    @Transient
    private boolean favorite;

    public Book() {
        this(null, null);
    }

    public Book(String id, String name) {
        this(id, name, false);
    }

    public Book(String id, String name, boolean favorite) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public Book asFavorite() {
        this.favorite = true;
        return this;
    }

}
