package com.cognitree.sangeet.contactServer.Model;

import java.util.Objects;

public class Contact {
    private long userId;
    private String name;
    private long phoneNumber;
    private String address;
    private String birthDate;
    private String email;

    public Contact() {

    }

    public Contact(long userId, String name, long phoneNumber, String address, String birthDate, String email) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return userId == contact.userId && phoneNumber == contact.phoneNumber && name.equals(contact.name) && address.equals(contact.address) && birthDate.equals(contact.birthDate) && email.equals(contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, phoneNumber, address, birthDate, email);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "userId='" + userId + '\'' +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
