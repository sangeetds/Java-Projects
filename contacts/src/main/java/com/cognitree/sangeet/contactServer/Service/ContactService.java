package com.cognitree.sangeet.contactServer.Service;

import com.cognitree.sangeet.contactServer.Enums.SearchFeature;
import com.cognitree.sangeet.contactServer.Model.Contact;
import com.cognitree.sangeet.contactServer.Repository.DatabaseRepository;

import java.util.List;

public class ContactService {
    private final DatabaseRepository contactRepository = new DatabaseRepository();

    public List<Contact> getContactByNumber(String phoneNumber, long userId) { return this.contactRepository.getContact(phoneNumber, SearchFeature.SEARCH_BY_NUMBER, userId); }

    public List<Contact> getContactByName(String name, long userId) { return this.contactRepository.getContact(name, SearchFeature.SEARCH_BY_NAME, userId); }

    public List<Contact> getAllContacts(long userId) {
        return this.contactRepository.getAllContact(userId);
    }

    public Contact addContact(Contact contact) {
        if (!this.contactRepository.addContact(contact)) {
            return null;
        }

        return contact;
    }

    public boolean deleteContact(long number, long id) {
        return this.contactRepository.deleteContact(number, id);
    }
}
