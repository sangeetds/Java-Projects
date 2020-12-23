package com.cognitree.sangeet.contactServer.Repository;

public interface Queries {
    String GET_USER_QUERY = "SELECT * FROM USER WHERE ID =";
    String GET_ALL_USER_QUERY = "SELECT * FROM user";
    String GET_ALL_CONTACTS_QUERY = "SELECT * FROM contact";
    String DELETE_CONTACTS_QUERY = "DELETE from CONTACT where USERID =";
    String GET_CONTACT_BY_NUMBER_QUERY = "SELECT * from CONTACT where PHONENUMBER like";
    String GET_CONTACT_BY_NAME_QUERY = "SELECT * from CONTACT where NAME like";
    String ADD_CONTACT_QUERY = "INSERT INTO contact" +
            "  (userId, name, phoneNumber, address, birthDate, email) VALUES " +
            " (?, ?, ?, ?, ?, ?);";
}
