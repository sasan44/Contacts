package com.leafstudio.contacts.model;

import java.util.Comparator;

public class ContactComparatorByName implements Comparator<Contact> {
    @Override
    public int compare(Contact contact, Contact t1) {
        return contact.getName().compareTo(t1.getName());
    }
}
