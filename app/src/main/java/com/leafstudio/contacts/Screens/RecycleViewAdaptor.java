package com.leafstudio.contacts.Screens;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leafstudio.contacts.R;
import com.leafstudio.contacts.databinding.RecycleLayoutBinding;
import com.leafstudio.contacts.model.Contact;
import com.leafstudio.contacts.model.ContactComparatorByName;

import java.util.Collections;
import java.util.List;

public class RecycleViewAdaptor extends Adapter<RecycleViewAdaptor.ViewHolder> { //implements INameableAdapter
    List<Contact> contacts;
    RecycleLayoutBinding binding;

    public RecycleViewAdaptor(List<Contact> contactss) {
        this.contacts = contactss;
    }

    void sort() {
        Collections.sort(contacts, new ContactComparatorByName());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycle_layout, parent, false);
        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.binding.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    deleteDialog(position);
            }
        });
        holder.binding.imageButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onPhoneCallClick(contacts.get(position).getNumber());
                }
            }
        });
        holder.binding.imageButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onSendTextClick(contacts.get(position).getNumber());
                }
            }
        });
        holder.binding.setContact(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        if (contacts != null)

            return contacts.size();
        else return 0;
    }

    public void notifyChange(List<Contact> contacts) {
        this.contacts = contacts;
        sort();
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecycleLayoutBinding binding;

        public ViewHolder(RecycleLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    void deleteDialog(final int position){
        AlertDialog alertDialog = new AlertDialog.Builder(binding.getRoot().getContext()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure you want to Delete ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            listener.onDeleteClick(contacts.get(position));
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private OnPhoneCallListener listener;

    public   void setListener(OnPhoneCallListener listener){
        this.listener = listener;
    }

    public interface OnPhoneCallListener{
        void onPhoneCallClick(String phoneNumber);
        void onDeleteClick(Contact contact);
        void onSendTextClick(String phoneNumber);
    }

}
