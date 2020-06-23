package br.booksapi.books.google;

import br.booksapi.books.google.configs.GoogleBooksConfigs;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

@Dependent
public class GoogleClientProviderImpl implements GoogleClientProvider {

    private static final String KEY_PARAM = "key";

    @Inject
    private GoogleBooksConfigs configs;

    @Override
    public GoogleClient getClient() {
        return new GoogleClient();
    }

    @Override
    public WebTarget getTarget(GoogleClient client) {
        return client.toTarget(configs.getHost()).queryParam(KEY_PARAM, configs.getKey());
    }
}
