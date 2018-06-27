package com.leafstudio.contacts.Screens;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leafstudio.contacts.Presenter.Presenter;
import com.leafstudio.contacts.R;
import com.leafstudio.contacts.application.ContactAppComponent;
import com.leafstudio.contacts.application.ContextModule;
import com.leafstudio.contacts.application.DaggerContactAppComponent;
import com.leafstudio.contacts.application.TimberLogImplementation;
import com.leafstudio.contacts.databinding.ActivityMainBinding;
import com.leafstudio.contacts.model.Contact;
import com.leafstudio.contacts.network.ApiService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UIListener{

    ActivityMainBinding binding;
    RecyclerView.LayoutManager layoutManager;
    ContactAppComponent component ;
    Presenter presenter ;
    ApiService apiService;
    RecycleViewAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimberLogImplementation.init();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        component = DaggerContactAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        presenter = component.getPresenter();
        apiService = component.getApiService();
        presenter.setListener(this);
          adaptor = new RecycleViewAdaptor(presenter.getContacts());
        binding.recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.recycleView.setLayoutManager(layoutManager);

        binding.recycleView.setAdapter(adaptor);
    }

    @Override
    public void update(List<Contact> contacts) {
        adaptor.notifyChange(contacts);
    }
}
