package br.booksapi.books.google.configs;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GoogleBooksConfigsProducer {

	private String host;

	private String key;

	public GoogleBooksConfigsProducer() {
		this(null, null);
	}

	@Inject
	public GoogleBooksConfigsProducer(@ConfigProperty(name = "GOOGLE_BOOKS_API_HOST", defaultValue = "https://www.googleapis.com/books/v1/") String host,
			@ConfigProperty(name = "GOOGLE_BOOKS_API_KEY", defaultValue = "") String key) {
		this.host = host;
		this.key = key;
	}

	@Produces
	public GoogleBooksConfigs produce() {
		return new GoogleBooksConfigs(this.host, this.key);
	}

}
