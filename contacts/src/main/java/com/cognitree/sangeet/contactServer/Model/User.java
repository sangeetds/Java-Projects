package com.cognitree.sangeet.contactServer.Model;

import java.util.List;
import java.util.Objects;

public class User {
    private long id;
    private String name;
    private List<Contact> contactsList;

    public User() {

    }

    public User(long id, String name, List<Contact> contactsList) {
        this.id = id;
        this.name = name;
        this.contactsList = contactsList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && contactsList.equals(user.contactsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contactsList);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", contactsList=" + contactsList +
                '}';
    }
}
