package com.leafstudio.contacts.Presenter;

import com.leafstudio.contacts.model.Contact;

import java.util.List;

public interface CallServerListener {
    void contactDeleted(String contact);

    void contactsLoaded(List<Contact> contacts);

    void contactLoaded(Contact contact);

    void contactCreated(Contact contact);

    void error(String error);
}
