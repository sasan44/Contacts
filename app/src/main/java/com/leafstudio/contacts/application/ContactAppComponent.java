package com.leafstudio.contacts.application;

import com.leafstudio.contacts.Presenter.Presenter;
import com.leafstudio.contacts.network.ApiService;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class  })
public interface  ContactAppComponent {

    Presenter getPresenter();

    ApiService getApiService();

}
