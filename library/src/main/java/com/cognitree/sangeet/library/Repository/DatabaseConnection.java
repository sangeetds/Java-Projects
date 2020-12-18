package com.cognitree.sangeet.library.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static Connection connection;

    private DatabaseConnection() {

    }

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            String jdbcURL = "jdbc:hsqldb:hsql://127.0.0.1/Test";
            String jdbcUsername = "SA";
            String jdbcPassword = "";
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
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
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
        } catch (SQLException throwables) {
            printSQLException(throwables);
        }
    }
}
