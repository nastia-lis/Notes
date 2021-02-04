package com.example.notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NotesFragment extends Fragment {
    public static final String NOTE_CURRENT = "Note";
    private Notes currentNote;
    private CardsNoteImpl cardsNote;
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_note);
        cardsNote = new CardsNoteImpl(getResources()).init();
        initRecycler(recyclerView, cardsNote);
        return view;
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            currentNote = getArguments().getParcelable(NOTE_CURRENT);
//        }
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NOTE_CURRENT, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(NOTE_CURRENT);
//        } else {
//            currentNote = new Notes(0, getResources().getStringArray(R.array.notes)[0]);
        }
    }

    private void initRecycler(RecyclerView recyclerView, CardsNote notes) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(notes);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            currentNote = new Notes(position, getResources().getStringArray(R.array.notes)[position]);
            showDescription(currentNote);
        });
    }

    private void showDescription(Notes currentNote) {
        if (isLandscape) {
            showLandNotes(currentNote);
        } else {
            showPortNotes(currentNote);
        }
    }

    private void showPortNotes(Notes currentNote) {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes, descriptionFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showLandNotes(Notes currentNote) {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.text_land, descriptionFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}