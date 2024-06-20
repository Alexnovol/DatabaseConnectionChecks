package steps.dataBaseSteps;

import entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ConnectionExecutor {

    private static SessionFactory sessionFactory = getSessionFactory();

    private static Session session;

    private ConnectionExecutor() {

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()//
                        .configure("hibernate.cfg.xml").build();

                // Create a metadata sources using the specified service registry.
                Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();

                return metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        if (session == null) {
            try {
                session = getSessionFactory().openSession();

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return session;
    }
}
