package com.leafstudio.contacts.Presenter;

import com.leafstudio.contacts.Screens.UIListener;
import com.leafstudio.contacts.model.Contact;
import com.leafstudio.contacts.network.CALLS;
import com.leafstudio.contacts.network.CallServer;

import java.util.List;

import timber.log.Timber;

public class Presenter implements CallServerListener, CALLS {
    List<Contact> contacts;
    CallServer callServer;
    UIListener uiListener;

    public Presenter(CallServer callServer) {
        Timber.d("Presenter");
        this.callServer = callServer;
        callServer.setListener(this);
        callServer.getAll();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @Override
    public void contactDeleted(String contact) {
        uiListener.notifyChange();
    }

    @Override
    public void contactsLoaded(List<Contact> contacts) {
        uiListener.update(contacts);
    }

    @Override
    public void contactLoaded(Contact contact) {

    }

    @Override
    public void contactCreated(Contact contact) {

    }

    @Override
    public void error(String error) {
        uiListener.error(error);
    }


    public void setListener(UIListener listener) {
        this.uiListener = listener;
    }

    @Override
    public void createContact(String name, String work, String number) {
        callServer.createContact(name, work, number);
    }

    @Override
    public void getContact(String name) {

    }

    @Override
    public void getAll() {
        callServer.getAll();
    }

    @Override
    public void deleteContact(String name) {
        callServer.deleteContact(name);
    }


}
