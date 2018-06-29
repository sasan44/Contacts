package com.leafstudio.contacts.Screens;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.leafstudio.contacts.Presenter.Presenter;
import com.leafstudio.contacts.R;
import com.leafstudio.contacts.databinding.FragmentDialogBinding;

public class AddDialogFragment extends DialogFragment {


    static AddDialogFragment newInstance() {
        AddDialogFragment f = new AddDialogFragment();
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentDialogBinding binding
                = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog, container, false);
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.editTextName.getText().length() > 1 && binding.editTextPhone.getText() != null
                        && binding.editTextWork.getText() != null
                        //&& validatePhoneNumber(binding.editTextPhone.getText().toString())
                        ) {
                    ((MainActivity) getActivity()).getPresenter().createContact(
                            "+"+binding.editTextName.getText().toString()
                            , binding.editTextWork.getText().toString()
                            , binding.editTextPhone.getText().toString());
                    dismiss();
                } else
                    warnEmpty();
            }

        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return binding.getRoot();
    }

    private boolean validatePhoneNumber(String s) {
        String Regex = "[^\\d]";//+33-123-563-2342
        String PhoneDigits = s.replaceAll(Regex, "");
        return (PhoneDigits.length() != 12);
    }

    void warnEmpty() {
        Toast.makeText(getActivity(), "ADD WORK , NAME AND PHONE!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * 0.75);
        int width = (int) (displayMetrics.widthPixels * 0.75);
        // Set the width of the dialog proportional to 75% of the screen width
        //  window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);


        // Call super onResume after sizing
        super.onResume();
    }

}
