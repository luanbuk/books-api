package br.booksapi.books;

import br.booksapi.arch.rest.Page;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

import static org.hibernate.criterion.MatchMode.ANYWHERE;

@Dependent
public class BooksMysqlRepositoryImpl implements BooksRepository {

    @Inject
    private EntityManager entityManager;

    @Transactional
    @Override
    public Collection<Book> list(String name, Page page) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Book> query = builder.createQuery(Book.class);
        final Root<Book> root = query.from(Book.class);

        return this.entityManager.createQuery(query.select(root).where(builder.like(root.get("name"), ANYWHERE.toMatchString(name))))
                .setFirstResult(page.getSize() * page.getCurrent())
                .setMaxResults(page.getSize()).getResultList();
    }

    @Transactional
    @Override
    public Optional<Book> getOne(String id) {
        try {
            final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Book> query = builder.createQuery(Book.class);
            final Root<Book> root = query.from(Book.class);

            query.select(root);
            query.where(builder.equal(root.get("id"), id));

            return Optional.of(this.entityManager.createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public boolean exists(String id) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<Book> root = query.from(Book.class);

        query.select(builder.count(root));
        query.where(builder.equal(root.get("id"), id));

        return this.entityManager.createQuery(query).getSingleResult() > 0;
    }

    @Transactional
    @Override
    public void insert(Book book) {
        this.entityManager.persist(book);
    }

    @Transactional
    @Override
    public void remove(String id) {
        this.getOne(id).ifPresent(book -> this.entityManager.remove(book));
    }


}
