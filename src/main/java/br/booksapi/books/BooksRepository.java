package br.booksapi.books;

import java.util.Collection;
import java.util.Optional;

import br.booksapi.arch.rest.Page;

public interface BooksRepository {
	
	Collection<Book> list(String name, Page page);
	
	Optional<Book> getOne(String id);

	boolean exists(String id);

    void insert(Book book);

	void remove(String id);
}
