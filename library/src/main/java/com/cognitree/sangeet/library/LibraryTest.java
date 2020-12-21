package com.cognitree.sangeet.library;

import com.cognitree.sangeet.library.Enum.Credentials;
import com.cognitree.sangeet.library.Repository.DatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class LibraryTest {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(LibraryTest.class.getName());

        Server server = new Server(Credentials.port);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/api/*");
        jerseyServlet.setInitOrder(1);

        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.cognitree.sangeet.library");

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            logger.error("Server cannot be started");
        } finally {
            server.destroy();
            DatabaseConnection.closeConnection();
        }
    }
}
