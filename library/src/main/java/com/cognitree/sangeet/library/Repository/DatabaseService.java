package com.cognitree.sangeet.library.Repository;

import com.cognitree.sangeet.library.Model.Book;
import com.cognitree.sangeet.library.Constants.TableFields;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseService {
    private final Connection connection;

    public DatabaseService() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Book> executeGetQuery(String query) {
        List<Book> initialBookList = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(TableFields.id);
                String title = resultSet.getString(TableFields.title);
                String author = resultSet.getString(TableFields.author);
                Boolean reserved = resultSet.getInt(TableFields.reserved) == 1;

                Book newBook = new Book((long) id, title, author, reserved);
                initialBookList.add(newBook);
            }
        } catch (SQLException e) {
            DatabaseConnection.printSQLException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwable) {
                    DatabaseConnection.printSQLException(throwable);
                }
            }
        }

        return initialBookList;
    }


    public void executeUpdateQuery(String query) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DatabaseConnection.printSQLException(e);
        }
    }
}
