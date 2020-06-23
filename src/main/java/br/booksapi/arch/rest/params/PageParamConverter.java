package br.booksapi.arch.rest.params;

import br.booksapi.arch.rest.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ParamConverter;
import java.io.IOException;

import static javax.json.Json.createObjectBuilder;

public class PageParamConverter implements ParamConverter<Page> {

    private final String CURRENT_TOKEN = "current";

    private final String SIZE_TOKEN = "size";

    @Override
    public Page fromString(String value) {
        try {
            final JsonNode pageNode = new ObjectMapper().readTree(value);

            return new Page(pageNode.get(SIZE_TOKEN).asInt(), pageNode.get(CURRENT_TOKEN).asInt());
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString(Page page) {
        return createObjectBuilder()
                .add(CURRENT_TOKEN, page.getCurrent())
                .add(SIZE_TOKEN, page.getSize())
                .build().toString();
    }
}
