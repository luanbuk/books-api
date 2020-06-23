package br.booksapi.books;

import br.booksapi.arch.rest.Page;

import java.util.List;
import java.util.Optional;

public interface BooksBoundary {

    List<Book> list(String query, Page page);

    Optional<Book> getOne(String id);

}
