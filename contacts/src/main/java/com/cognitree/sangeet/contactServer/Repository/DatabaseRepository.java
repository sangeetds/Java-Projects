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

    public List<User> getAllUsers() {
        String query = Queries.GET_ALL_USER_QUERY;
        return this.databaseService.executeGetUserQuery(query);
    }

    public List<Contact> getAllContact() {
        String query = Queries.GET_ALL_CONTACTS_QUERY;

        return this.databaseService.executeGetContactQuery(query);
    }

    public User getUser(long id) {
        String query = Queries.GET_USER_QUERY + id + ";";
        List<User> users = this.databaseService.executeGetUserQuery(query);

        if (users.isEmpty()) return null;

        return users.get(0);
    }

    public List<Contact> getContact(String id, SearchFeature feature) {
        String query = (feature.equals(SearchFeature.SEARCH_BY_NAME)) ? Queries.GET_CONTACT_BY_NAME_QUERY + "'%" + id + "%';" :
                Queries.GET_CONTACT_BY_NUMBER_QUERY + "'%" + id + "%';";

        List<Contact> contacts = this.databaseService.executeGetContactQuery(query);

        if (contacts.isEmpty()) return null;

        return contacts;
    }


    public boolean addContact(Contact contact) {
        return this.databaseService.addContact(contact);
    }

    public boolean deleteContact(long userId) {
        String query = Queries.DELETE_CONTACTS_QUERY + userId + ";";
        return this.databaseService.deleteContact(query) > 0;
    }
}
