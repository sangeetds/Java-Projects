package com.cognitree.sangeet.contactServer.Repository;

import com.cognitree.sangeet.contactServer.Model.Contact;
import com.cognitree.sangeet.contactServer.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DatabaseService {
    private final Connection connection;

    public DatabaseService() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<User> executeGetUserQuery(String query) {
        List<User> userList = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String pass = resultSet.getString("pass");

                User newUser = new User(id, name, pass);
                userList.add(newUser);
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

        return userList;
    }

    public List<Contact> executeGetContactQuery(String query) {
        List<Contact> contactList = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long userId = resultSet.getLong("userId");
                String name = resultSet.getString("name");
                long number = resultSet.getLong("phoneNumber");
                String address = resultSet.getString("address");
                String birthDate = resultSet.getString("birthDate");
                String email = resultSet.getString("email");

                Contact newContact = new Contact(userId, name, number, address, birthDate, email);
                contactList.add(newContact);
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

        return contactList;
    }


    public void executeUpdateQuery(String query) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DatabaseConnection.printSQLException(e);
        }
    }

    public boolean addContact(Contact contact) {
        String query = Queries.ADD_CONTACT_QUERY;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, contact.getUserId());
            preparedStatement.setString(2, contact.getName());
            preparedStatement.setString(3, String.valueOf(contact.getPhoneNumber()));
            preparedStatement.setString(4, contact.getAddress());
            preparedStatement.setString(5, contact.getBirthDate());
            preparedStatement.setString(6, contact.getEmail());

            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            DatabaseConnection.printSQLException(ex);
            return false;
        }

        return true;
    }

    public boolean addUser(User user) {
        String query = Queries.ADD_USER_QUERY;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(6, user.getPass());

            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            DatabaseConnection.printSQLException(ex);
            return false;
        }

        return true;
    }

    public int executeDeleteQuery(String query) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate();
        }
        catch (SQLException exception) {
            DatabaseConnection.printSQLException(exception);
        }

        return 0;
    }
}
