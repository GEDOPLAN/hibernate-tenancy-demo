package de.gedoplan.showcase.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EntityManager-Producer.
 *
 * @author dw
 */
@ApplicationScoped
public class EntityManagerProducer {
  @PersistenceContext
  @Produces
  EntityManager entityManager;
}
