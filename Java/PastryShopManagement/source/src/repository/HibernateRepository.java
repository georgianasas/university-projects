package repository;

import domain.Entity;
import exceptions.RepositoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class HibernateRepository<T extends Entity> implements Repository<T> {
    private static SessionFactory sessionFactory;
    private final Class<T> type;

    public HibernateRepository(Class<T> type) {
        this.type = type;
        initialize();
    }

    private static void initialize() {
        if (sessionFactory == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(registry);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void add(T element) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            if (session.get(type, element.getID()) != null) {
                throw new RepositoryException("Duplicate ID: " + element.getID());
            }
            session.persist(element);
            tx.commit();
        }
    }

    @Override
    public void update(T element) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(element);
            tx.commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            T item = session.get(type, id);
            if (item != null) {
                session.remove(item);
                tx.commit();
            }
        }
    }

    @Override
    public T findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(type, id);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + type.getName(), type).list();
        }
    }
}