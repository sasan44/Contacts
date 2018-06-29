package com.leafstudio.contacts.Screens;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements UIListener {
    AnimationDrawable animationDrawable;
    ActivityMainBinding binding;
    RecyclerView.LayoutManager layoutManager;
    ContactAppComponent component;
    Presenter presenter;
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
                showAddContactDialog();
            }
        });
        presenter = component.getPresenter();
        apiService = component.getApiService();
        presenter.setListener(this);
        adaptor = new RecycleViewAdaptor(presenter.getContacts());
        binding.recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setAdapter(adaptor);
        binding.recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(itemDecoration);

        View rootView = findViewById(android.R.id.content);
        rootView.setBackground(getResources().getDrawable(R.drawable.gradient_list));
        animationDrawable = (AnimationDrawable) rootView.getBackground();
        configAnime(animationDrawable);

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        binding.fastScroller.setRecyclerView(binding.recyclerView);
        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        binding.recyclerView.setOnScrollListener(binding.fastScroller.getOnScrollListener());

        adaptor.setListener(new RecycleViewAdaptor.OnPhoneCallListener() {
            @Override
            public void onPhoneCallClick(String phoneNumber) {
                callNumber(phoneNumber);
            }

            @Override
            public void onDeleteClick(Contact contact) {
                presenter.deleteContact(contact.getName());
            }

            @Override
            public void onSendTextClick(String phoneNumber) {
                // sendSMSMessage(phoneNumber);
                willAddFeature();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    if (tmpNumber != null)
                        callNumber(tmpNumber);
                } else {
                    // permission denied
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //   sendSMSMessage();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }


        }
    }

    @Override
    public void update(List<Contact> contacts) {
        adaptor.notifyChange(contacts);
    }

    @Override
    public void error(String string) {
        Toast.makeText(getApplicationContext(),
                string + " faild, please try again.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyChange() {
        adaptor.notifyDataSetChanged();
    }

    void showAddContactDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        DialogFragment newFragment = AddDialogFragment.newInstance();
        newFragment.show(ft, "dialog");
    }

    void configAnime(AnimationDrawable animationDrawable) {
        animationDrawable.setAlpha(100);
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    public Presenter getPresenter() {
        return presenter;
    }

    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
    String tmpNumber = null;

    private void callNumber(String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            tmpNumber = phoneNumber;

        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        }
    }

    public void willAddFeature() {
        Toast.makeText(this, "Feature will be ready soon !!", Toast.LENGTH_LONG).show();
    }

    //    protected void sendSMSMessage(String phoneNumber) {
//       // message = txtMessage.getText().toString();
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
//        } else {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
//        }
//    }

}
