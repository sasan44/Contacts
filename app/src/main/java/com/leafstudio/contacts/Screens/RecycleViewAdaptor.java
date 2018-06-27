package com.leafstudio.contacts.Screens;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leafstudio.contacts.R;
import com.leafstudio.contacts.databinding.RecycleLayoutBinding;
import com.leafstudio.contacts.model.Contact;

import java.util.List;

public class RecycleViewAdaptor extends Adapter<RecycleViewAdaptor.ViewHolder> {
    List<Contact> contacts  ;

    public RecycleViewAdaptor(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycle_layout, parent, false);
        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.binding.imageButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.binding.textViewName.setText("" + contacts.get(position).getName());
        holder.binding.textViewNumber.setText(""+ contacts.get(position).getNumber());
        holder.binding.textViewWork.setText(""+ contacts.get(position).getWork());
        holder.binding.textViewCreatedAt.setText(""+ contacts.get(position).getCreatedAt());
        holder.binding.textViewUpdatedAt.setText(""+ contacts.get(position).getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        if(contacts!=null)

        return contacts.size();
    else  return  0 ;
    }

    public void notifyChange(List<Contact> contacts) {
        this.contacts = contacts;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecycleLayoutBinding binding;

        public ViewHolder(RecycleLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
