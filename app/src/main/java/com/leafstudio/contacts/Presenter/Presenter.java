package com.leafstudio.contacts.Presenter;

import com.leafstudio.contacts.Screens.UIListener;
import com.leafstudio.contacts.model.Contact;
import com.leafstudio.contacts.network.CallServer;

import java.util.List;

import timber.log.Timber;

public class Presenter implements CallServerListener{
    List<Contact> contacts;
    CallServer callServer;
    public Presenter(CallServer callServer ){
        Timber.d("Presenter");
        this.callServer = callServer;
        callServer.setListener(this);
        callServer.getAll();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @Override
    public void contactDeleted(Contact contact) {

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

    UIListener uiListener ;
    public void setListener(UIListener listener) {
        this.uiListener = listener;
    }
}
