package com.sba.sms.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private HibernateUtil() {
        // Utility classes should not have public constructors
    }

    /**
     * @Getter builds a standard getter method for the object
     * sessionFactory.
     */
    @Getter
    private static SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Method builds a session factory from the 'hibernate.cfg.xml' file
     * in the 'resources' folder and returns a sessionFactory object.
     * @return
     */
    private static SessionFactory buildSessionFactory()
    {
        try
        {
            if (sessionFactory == null)
            {
                StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml").build();

                Metadata metaData = new MetadataSources(standardRegistry)
                        .getMetadataBuilder()
                        .build();

                sessionFactory = metaData.getSessionFactoryBuilder().build();
            }
            return sessionFactory;
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Closes the session factory.
     */
    public static void shutdown() {
        getSessionFactory().close();
    }


}
