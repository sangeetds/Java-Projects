import com.cognitree.sangeet.contactServer.Enums.ContactServerProperties;
import com.cognitree.sangeet.contactServer.Repository.DatabaseConnection;
import org.eclipse.jetty.server.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class ContactsTest {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(ContactsTest.class.getName());

        Server server = new Server(ContactServerProperties.port);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(1);

        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.cognitree.sangeet.contactServer");

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            server.destroy();
            DatabaseConnection.closeConnection();
        }
    }
}
