package br.booksapi.books.google;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.client.WebTarget;

import br.booksapi.arch.rest.Page;
import br.booksapi.books.Book;
import br.booksapi.books.BooksBoundary;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Dependent
public class BooksGoogleBoundaryImpl implements BooksBoundary {

	private final GoogleClientProvider clientProvider;

	private final String ROOT_PATH = "volumes";

	private final String QUERY_PARAM = "q";

	private final String START_IDX_PARAM = "startIndex";

	private final String MAX_RESULTS_PARAM = "maxResults";

	private final BooksDeserializer deserializer = new BooksDeserializer();

	@Inject
	public BooksGoogleBoundaryImpl(GoogleClientProvider clientProvider) {
		this.clientProvider = clientProvider;
	}

	@Override
	public List<Book> list(String query, Page page) {
		try(GoogleClient client = clientProvider.getClient()){

			final WebTarget target = clientProvider.getTarget(client).path(ROOT_PATH).queryParam(QUERY_PARAM, query)
					.queryParam(START_IDX_PARAM, page.getCurrent()).queryParam(MAX_RESULTS_PARAM, page.getSize());

			return Optional.ofNullable(target.request(APPLICATION_JSON).get(String.class))
					.map(this.deserializer::deserializeList).orElse(emptyList());
		} catch (Exception e){
			e.printStackTrace();
			return emptyList();
		}
	}

	@Override
	public Optional<Book> getOne(String id) {
		try(GoogleClient client = clientProvider.getClient()){

			final WebTarget target = clientProvider.getTarget(client).path(ROOT_PATH).path(id);

			return Optional.ofNullable(target.request(APPLICATION_JSON).get(String.class))
					.map(this.deserializer::deserializeSingle);
		} catch (NotFoundException | ServiceUnavailableException e){
			return Optional.empty();
		}  catch (Exception e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

}
