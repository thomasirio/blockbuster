package com.github.thomasirio.util;

import com.github.thomasirio.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class DBHandler implements ServletContextListener {
  private static SessionFactory dbSessionFactory;

  /**
   * openSessionFactory generates a new session factory using the configurations
   * setted as environment variable on the destination system.
   *
   * Please set DB_URL, DB_USERNAME, DB_PASSWORD variables on the server.
   */
  public static void openSessionFactory() {
    if(DBHandler.dbSessionFactory != null)
      return;

    AnnotationConfiguration config = new AnnotationConfiguration()
            .setProperty("hibernate.connection.url", Properties.get(Properties.DB_URL))
            .setProperty("hibernate.connection.username", Properties.get(Properties.DB_USERNAME))
            .setProperty("hibernate.connection.password", Properties.get(Properties.DB_PASSWORD))
            .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
            .addAnnotatedClass(Attore.class)
            .addAnnotatedClass(Film.class)
            .addAnnotatedClass(Profilo.class)
            .addAnnotatedClass(Regista.class)
            .addAnnotatedClass(Utente.class)
        ;

    if(Properties.get(Properties.TEST_ENV) != null)
      config.setProperty("hibernate.hbm2ddl.auto", "create");

    DBHandler.dbSessionFactory = config.buildSessionFactory();
  }

  /**
   * contextInitialized is called once the servlet container is ready.
   * It calls openSessionFactory in order to generate the dbSessionFactory
   * object.
   *
   * @param servletContextEvent is unused in this context.
   */
  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    DBHandler.openSessionFactory();
    System.out.println("DB session factory opened!");
  }

  /**
   * contextDestroyed is called once the servlet container is closed.
   * It calls closeSessionFactory in order to destroy the dbSessionFactory
   * object.
   *
   * @param servletContextEvent is unused in this context.
   */
  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    DBHandler.closeSessionFactory();
    System.out.println("DB session factory closed!");
  }

  /**
   * closeSessionFactory destroys the current session factory object
   */
  public static void closeSessionFactory() {
    if(DBHandler.dbSessionFactory == null)
      return;

    DBHandler.dbSessionFactory.close();
    DBHandler.dbSessionFactory = null;
  }

  /**
   * getDbSession generates a new session object from the factory that can be used
   * to connect to the db
   *
   * @return the session object
   */
  public static Session getDbSession() {
    if(DBHandler.dbSessionFactory == null)
      DBHandler.openSessionFactory();

    return DBHandler.dbSessionFactory.openSession();
  }
}
