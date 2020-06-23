package br.booksapi.books.google.configs;

public class GoogleBooksConfigs {

	private final String host;

	private final String key;

	public GoogleBooksConfigs(String host, String key) {
		this.host = host;
		this.key = key;
	}

	public String getHost() {
		return host;
	}

	public String getKey() {
		return key;
	}

}
