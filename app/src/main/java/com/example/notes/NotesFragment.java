package com.example.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {
    public static final String NOTE_CURRENT = "Note";
    private Notes currentNote;
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNote(view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NOTE_CURRENT, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showLandNotes(currentNote);
        }
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(NOTE_CURRENT);
        } else {
            currentNote = new Notes(0, getResources().getStringArray(R.array.notes)[0]);
        }
    }

    private void initNote(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);
        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView textNote = new TextView(getContext());
            textNote.setText(note);
            textNote.setTextSize(30);
            layoutView.addView(textNote);
            final int fi = i;
            textNote.setOnClickListener(v -> {
                currentNote = new Notes(fi, getResources().getStringArray(R.array.notes)[fi]);
                showDescription(currentNote);
            });
        }
    }

    private void showDescription(Notes currentNote) {
        if (isLandscape) {
            showLandNotes(currentNote);
        } else {
            showPortNotes(currentNote);
        }
    }

    private void showPortNotes(Notes currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DescriptionActivity.class);
        intent.putExtra(DescriptionFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }

    private void showLandNotes(Notes currentNote) {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.text_description, descriptionFragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}