package com.leafstudio.contacts.network;

public interface CALLS {

    void createContact(String name, String work, String number);
    void getContact(String name);
    void getAll();
    void deleteContact(String name);
}
