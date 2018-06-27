package com.leafstudio.contacts.network;

import com.google.gson.Gson;
import com.leafstudio.contacts.Presenter.CallServerListener;
import com.leafstudio.contacts.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class CallServer {

    ApiService apiService;

    public CallServer(ApiService apiService) {
        this.apiService = apiService;
    }

    public void createContact(String name, String work, String number) {
        Call<Contact> call = apiService.createContact(name, work, number);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                Timber.d("onResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    //     listener.onContactsLoaded(response.body() );
                    contactCreated(response.body());
                } else
                    Timber.d("NULL " + call.toString());
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Timber.d("onFailure" + call.toString());
                t.printStackTrace();
            }
        });
    }

    public void getContact(String name) {
        Call<Contact> call = apiService.getContact(name);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                Timber.d("onResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    contactLoaded(response.body());
                } else
                    Timber.d("NULL " + call.toString());
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Timber.d("onFailure" + call.toString());
                t.printStackTrace();
            }
        });
    }

    public void getAll() {
        Call<List<Contact>> call = apiService.getAll();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.body() != null) {
                    contactsLoaded(response.body());
                } else Timber.d("NULL " + call.toString());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Timber.d("onFailure" + call.toString());
                t.printStackTrace();
            }
        });
    }

    public void deleteContact(String name) {
        Call<Contact> call = apiService.deleteContact(name);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                Timber.d("onResponse" + new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Timber.d("onFailure" + call.toString());
                t.printStackTrace();
            }
        });
    }

    private void contactsLoaded(List<Contact> body) {
        listener.contactsLoaded( body);
    }

    private void contactLoaded(Contact body) {
        listener.contactLoaded(body);
    }

    private void contactCreated(Contact body) {
        listener.contactCreated(body);
    }

    CallServerListener listener ;
    public void setListener(CallServerListener listener) {
        this.listener = listener;
    }
}
