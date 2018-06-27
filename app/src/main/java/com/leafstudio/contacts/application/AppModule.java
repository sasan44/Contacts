package com.leafstudio.contacts.application;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leafstudio.contacts.Presenter.Presenter;
import com.leafstudio.contacts.network.ApiService;
import com.leafstudio.contacts.network.CallServer;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {NetworkModule.class})
public class AppModule {
    @Provides
    @AppScope
    public ApiService getApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    private static final String BASE_URL = "http://188.166.10.67:18888";

    @Provides
    @AppScope
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

    @Provides
    @AppScope
    public static Retrofit getClient(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @AppScope
    Presenter getPresenter(CallServer callServer  ) {
        return new Presenter(callServer );
    }

    @Provides
    @AppScope
    CallServer getCallServer(ApiService apiService  ) {
        return new CallServer(apiService );
    }
}
