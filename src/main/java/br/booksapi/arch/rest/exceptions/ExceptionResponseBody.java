package br.booksapi.arch.rest.exceptions;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.WebApplicationException;

public class ExceptionResponseBody {

    public static String build(WebApplicationException exception) {
        return Json.createObjectBuilder().add("error", exception.getMessage()).build().toString();
    }
}
