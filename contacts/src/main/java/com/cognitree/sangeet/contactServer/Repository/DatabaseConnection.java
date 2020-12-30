package com.cognitree.sangeet.contactServer.Repository;

import com.cognitree.sangeet.contactServer.Enums.ContactServerProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to instantiate connection with the database and finally close the connection.
 * Is made in respect to Singleton Pattern such that only one instance of the class
 * exists in the whole project.
 */
public class DatabaseConnection {

    static Connection connection;
    static Logger logger = LogManager.getLogger(DatabaseConnection.class.getName());

    private DatabaseConnection() {

    }

    /**
     * Function to connect with the database.
     * @return Connection object which can be later used to interact with the database.
     */
    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            String jdbcURL = ContactServerProperties.jdbcUrl;
            String jdbcUsername = ContactServerProperties.jdbcUser;
            String jdbcPassword = ContactServerProperties.jdbcPass;
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            System.out.println("Connection made successfully.");
        } catch (SQLException e) {
            printSQLException(e);
        }

        return connection;
    }

    /**
     * Function to log any error messages.
     * @param ex the exception encountered.
     */
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                logger.error("SQLState: " + ((SQLException) e).getSQLState());
                logger.error("Error Code: " + ((SQLException) e).getErrorCode());
                logger.error("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    /**
     * Function to finally close the connection to database to avoid memory leakages.
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwable) {
            printSQLException(throwable);
        }
    }
}
