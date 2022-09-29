package Empresa;
/**
 * @author Prof Matias Garcia.
 * <p> Copyright (C) 2021 para <a href = "https://www.profmatiasgarcia.com.ar/"> www.profmatiasgarcia.com.ar </a>
 * - con licencia GNU GPL3.
 * <p> Este programa es software libre. Puede redistribuirlo y/o modificarlo bajo los términos de la
 * Licencia Pública General de GNU según es publicada por la Free Software Foundation, 
 * bien con la versión 3 de dicha Licencia o bien (según su elección) con cualquier versión posterior. 
 * Este programa se distribuye con la esperanza de que sea útil, pero SIN NINGUNA GARANTÍA, 
 * incluso sin la garantía MERCANTIL implícita o sin garantizar la CONVENIENCIA PARA UN PROPÓSITO
 * PARTICULAR. Véase la Licencia Pública General de GNU para más detalles.
 * Debería haber recibido una copia de la Licencia Pública General junto con este programa. 
 * Si no ha sido así ingrese a <a href = "http://www.gnu.org/licenses/"> GNU org </a>
 */
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static Session session;

    static {
        try {
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

            Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

            sessionFactory = meta.getSessionFactoryBuilder().build();

            session = sessionFactory.openSession();

        } catch (HibernateException ex) {
            System.err.println("Error al crear la conf de hibernate: " + ex.getMessage());
            throw new ExceptionInInitializerError();
        }
    }

    /**
     * Devuelve la sessionFactory
     * @return 
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Abre una nueva sesión
     */
    public static void openSession() {
        try {
            session = sessionFactory.openSession();
        } catch (HibernateException ex) {
            System.err.println("Error al abrir una sesión de hibernate: " + ex.getMessage());
            throw new ExceptionInInitializerError();
        }

    }

    /**
     * Devuelve la sesión actual
     * @return 
     */
    public static Session getCurrentSession() {
        if ((session == null) || (!session.isOpen())) {
            openSession();
        }
        return session;
    }

    /**
     * Cierra la sesion y la sessionFactory
     */
    public static void closeSession() {
        if (session != null) {
            session.close();
        }

        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
