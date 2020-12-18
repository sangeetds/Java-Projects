package com.cognitree.sangeet.library.Repository;

import com.cognitree.sangeet.library.Enum.Action;
import com.cognitree.sangeet.library.Model.Book;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseService {
    private final Connection connection;

    public DatabaseService() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Book> executeGetQuery(String QUERY) {
        System.out.println(QUERY);
        List<Book> initialBookMap = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                Boolean reserved = rs.getInt("reserved") == 1;

                Book newBook = new Book((long) id, title, author, reserved);
                initialBookMap.add(newBook);
            }

            rs.close();
        } catch (SQLException e) {
            DatabaseConnection.printSQLException(e);
        }

        return initialBookMap;
    }


    public void executeUpdateQuery(String QUERY_TWO) {
        System.out.println(QUERY_TWO);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TWO);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DatabaseConnection.printSQLException(e);
        }
    }
}
