package com.example.view.todo.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.view.todo.R;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Integer image;

    public CustomSpinnerAdapter(@NonNull Context context, String categories [], Integer image) {
        super(context, 0, categories);
        this.image = image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.my_spinner_dropdown_item, parent, false);
        }

        ImageView imageViewSpinner = convertView.findViewById(R.id.imageViewSpinner);
        TextView textViewSpinner = convertView.findViewById(R.id.textViewCategorySpinner);

        imageViewSpinner.setImageResource(image);
        textViewSpinner.setText(getItem(position));

        return convertView;
    }
}
