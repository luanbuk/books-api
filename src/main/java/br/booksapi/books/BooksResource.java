package br.booksapi.books;

import br.booksapi.arch.rest.Page;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

import static br.booksapi.arch.rest.RestPatterns.CONSUMES_JSON;
import static br.booksapi.arch.rest.RestPatterns.PRODUCES_JSON;
import static java.util.stream.Collectors.toList;

@Path("books")
@Consumes(CONSUMES_JSON)
@Produces(PRODUCES_JSON)
@RequestScoped
public class BooksResource {

    private final String PAGE_MANDATORY_MSG = "Page query param is a Json object (format:{ \"current\": 0, \"size\": 0 })";

    private final String PAGE_SIZE_LIMIT_MGS = "Page size is limited to 20 records";

    private final String NAME_MANDATORY_MSG = "\"name\" is a mandatory field";

    private final String BOOK_NOT_FOUND_MSG = "Book not found";

    @Inject
    private BooksRepository repository;

    @Inject
    private BooksBoundary boundary;

    @GET
    public Collection<Book> list(@QueryParam("name") String name, @QueryParam("page") Page page) {
        this.validateFilters(name, page);
        return this.boundary.list(name, page).stream().map(b -> this.repository.exists(b.getId()) ? b.asFavorite() : b ).collect(toList());
    }

    @GET
    @Path("/{id}")
    public Book getOne(@PathParam("id") String id) {
        return boundary.getOne(id).orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MSG));
    }

    @GET
    @Path("/with-stars")
    public Collection<Book> getFavorite(@QueryParam("name") String name, @QueryParam("page") Page page) {
        this.validateFilters(name, page);
        return this.repository.list(name, page).stream().map(Book::asFavorite).collect(toList());
    }

    @POST
    @Path("/{id}/favorite")
    public Response setFavorite(@PathParam("id") String id) {
        final Book book = boundary.getOne(id).orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MSG));

        if(!this.repository.exists(id)){
            this.repository.insert(book);
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}/favorite")
    public Response removeFavorite(@PathParam("id") String id) {
        if(!this.repository.exists(id)){
            throw new NotFoundException(BOOK_NOT_FOUND_MSG);
        }

        this.repository.remove(id);

        return Response.ok().build();
    }

    private void validateFilters(String name, Page page){
        Optional.ofNullable(page).orElseThrow(() -> new BadRequestException(PAGE_MANDATORY_MSG));
        Optional.of(page).filter(p -> p.getSize() <= 20).orElseThrow(() -> new BadRequestException(PAGE_SIZE_LIMIT_MGS));
        Optional.ofNullable(name).filter(StringUtils::isNotBlank).orElseThrow(() -> new BadRequestException(NAME_MANDATORY_MSG));
    }
}
