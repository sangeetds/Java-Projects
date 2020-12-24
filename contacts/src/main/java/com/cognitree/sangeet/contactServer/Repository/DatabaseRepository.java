package com.cognitree.sangeet.contactServer.Repository;

import com.cognitree.sangeet.contactServer.Enums.SearchFeature;
import com.cognitree.sangeet.contactServer.Model.Contact;
import com.cognitree.sangeet.contactServer.Model.User;

import java.util.*;

public class DatabaseRepository {
    private final DatabaseService databaseService;

    public DatabaseRepository() {
        this.databaseService = new DatabaseService();
    }

    public List<Contact> getAllContact(long userId) {
        String query = Queries.GET_ALL_CONTACTS_QUERY + userId + ";";

        return this.databaseService.executeGetContactQuery(query);
    }

    public List<Contact> getContact(String value, SearchFeature feature, long userId) {
        String query =
                (feature.equals(SearchFeature.SEARCH_BY_NAME)) ?
                        Queries.GET_CONTACT_BY_NAME_QUERY + "'%" + value + "%' and USERID = " + userId + ";" :
                Queries.GET_CONTACT_BY_NUMBER_QUERY + "'%" + value + "%' and USERID = " + userId + ";";

        List<Contact> contacts = this.databaseService.executeGetContactQuery(query);

        if (contacts.isEmpty()) return null;

        return contacts;
    }


    public boolean addContact(Contact contact) {
        return this.databaseService.addContact(contact);
    }

    public boolean deleteContact(long number, long id) {
        String query = Queries.DELETE_CONTACT_QUERY + number + "and userId = " + id + ";";
        return this.databaseService.executeDeleteQuery(query) > 0;
    }

    public List<User> getAllUsers() {
        String query = Queries.GET_ALL_USER_QUERY;
        return this.databaseService.executeGetUserQuery(query);
    }

    public User getUser(long id) {
        String query = Queries.GET_USER_QUERY + id + ";";
        List<User> users = this.databaseService.executeGetUserQuery(query);

        if (users.isEmpty()) return null;

        return users.get(0);
    }

    public boolean addUser(User user) {
        return this.databaseService.addUser(user);
    }

    public boolean deleteUser(long id) {
        String query = Queries.DELETE_USER_QUERY + id + ";";
        return this.databaseService.executeDeleteQuery(query) > 0;
    }
}
