package com.example.notes;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DescriptionFragment extends Fragment {
    public static final String ARG_NOTE = "Note";
    private Notes notes;

    public static DescriptionFragment newInstance(Notes notes) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, notes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notes = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        TextView textDescription = view.findViewById(R.id.text_description);
        TypedArray description = getResources().obtainTypedArray(R.array.description_note);
        textDescription.setText(description.getResourceId(notes.getIndexNote(), -1));
        return view;
    }
}