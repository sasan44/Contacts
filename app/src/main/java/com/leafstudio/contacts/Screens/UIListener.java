package com.leafstudio.contacts.Screens;

import com.leafstudio.contacts.model.Contact;

import java.util.List;

public interface UIListener {
    void update(List<Contact> contacts);
}
