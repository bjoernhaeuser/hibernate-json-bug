package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.bugs.entity.JsonHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase
{
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @After
    public void destroy()
    {
        entityManagerFactory.close();
    }

    // Entities are auto-discovered, so just add them anywhere on class-path
    // Add your tests, using standard JUnit.
    @Test
    public void hhh17660Test_with_long() throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        var jsonHolder = new JsonHolder(1L, Map.of("property1", 1L));
        jsonHolder.updated();
        entityManager.persist(jsonHolder);
        entityManager.flush();

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        var saved = entityManager.find(JsonHolder.class, 1L);

        Assertions.assertEquals(0L, saved.getVersion());

        saved.setProperties(Map.of("property1", 1L));
        saved.updated();

        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        saved = entityManager.find(JsonHolder.class, 1L);
        Assertions.assertEquals(1L, saved.getVersion());

        entityManager.close();
    }

    @Test
    public void hhh17660Test_with_integer() throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        var jsonHolder = new JsonHolder(1L, Map.of("property1", 1));
        jsonHolder.updated();
        entityManager.persist(jsonHolder);
        entityManager.flush();

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        var saved = entityManager.find(JsonHolder.class, 1L);

        Assertions.assertEquals(0L, saved.getVersion());

        saved.setProperties(Map.of("property1", 1));
        saved.updated();

        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        saved = entityManager.find(JsonHolder.class, 1L);
        Assertions.assertEquals(1L, saved.getVersion());

        entityManager.close();
    }
}
