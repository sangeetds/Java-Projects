package com.cognitree.sangeet.library.Repository;

import com.cognitree.sangeet.library.Enum.Credentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static Connection connection;
    static Logger logger = LogManager.getLogger(DatabaseConnection.class.getName());

    private DatabaseConnection() {

    }

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            String jdbcURL = Credentials.jdbcUrl;
            String jdbcUsername = Credentials.jdbcUser;
            String jdbcPassword = Credentials.jdbcPass;
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            System.out.println("Connection made successfully.");
        } catch (SQLException e) {
            printSQLException(e);
        }

        return connection;
    }

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

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwable) {
            printSQLException(throwable);
        }
    }
}
