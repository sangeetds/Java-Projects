package com.cognitree.sangeet.contactServer.Service;

import com.cognitree.sangeet.contactServer.Enums.SearchFeature;
import com.cognitree.sangeet.contactServer.Model.Contact;
import com.cognitree.sangeet.contactServer.Repository.DatabaseRepository;

import java.util.List;

public class ContactService {
    private final DatabaseRepository contactRepository = new DatabaseRepository();

    public List<Contact> getContactByNumber(String phoneNumber) { return this.contactRepository.getContact(phoneNumber, SearchFeature.SEARCH_BY_NUMBER); }

    public List<Contact> getContactByName(String name) { return this.contactRepository.getContact(name, SearchFeature.SEARCH_BY_NAME); }

    public List<Contact> getAllContacts() {
        return this.contactRepository.getAllContact();
    }

    public Contact addContact(Contact contact) {
        if (!this.contactRepository.addContact(contact)) {
            return null;
        }

        return contact;
    }

    public boolean deleteContact(long userId) {
        return this.contactRepository.deleteContact(userId);
    }
}
