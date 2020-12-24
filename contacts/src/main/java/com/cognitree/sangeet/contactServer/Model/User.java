package com.cognitree.sangeet.contactServer.Model;

import java.util.List;
import java.util.Objects;

public class User {
    private long id;
    private String name;
    private String pass;

    public User() {

    }

    public User(long id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && name.equals(user.name) && pass.equals(user.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pass);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
