package com.leafstudio.contacts.network;

import com.leafstudio.contacts.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    @POST("contact")
    Call<Contact> createContact(
            @Query("name") String name,
            @Query("work") String work,
            @Query("number") String number
    );

    @GET("contact/{name}")
    Call<Contact> getContact(
            @Path("name") String name
    );

    @GET("contact")
    Call<List<Contact>> getAll(
    );

    @DELETE("{contact/{name}")
    Call deleteContact(
            @Path("name") String name
    );

}
