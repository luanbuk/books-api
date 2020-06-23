package br.booksapi.arch.database;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.Produces;
import java.util.Optional;

@ApplicationScoped
public class EntityManagerProducer {

    private static final String PERSISTENCE_UNIT = "books";

    private EntityManager entityManager;

    @Produces
    public EntityManager getEntityManager(){
        return Optional.ofNullable(this.entityManager).orElseGet(this::createNew);
    }

    private EntityManager createNew(){
        this.entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
        return this.entityManager;
    }

}
