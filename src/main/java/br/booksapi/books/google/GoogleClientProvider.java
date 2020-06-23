package br.booksapi.books.google;

import javax.ws.rs.client.WebTarget;

public interface GoogleClientProvider {

    GoogleClient getClient();

    WebTarget getTarget(GoogleClient client);
}
