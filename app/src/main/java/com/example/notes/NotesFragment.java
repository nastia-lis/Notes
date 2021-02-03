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
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_note);
        String[] notes = getResources().getStringArray(R.array.notes);
        initRecycler(recyclerView, notes);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initNote(view);
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
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(NOTE_CURRENT);
        } else {
            currentNote = new Notes(0, getResources().getStringArray(R.array.notes)[0]);
        }
        if (isLandscape) {
            showLandNotes(currentNote);
        }
    }

    private void initRecycler(RecyclerView recyclerView, String[] notes) {
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
//    private void initNote(View view) {
//        LinearLayout layoutView = (LinearLayout) view;
//        String[] notes = getResources().getStringArray(R.array.notes);
//        for (int i = 0; i < notes.length; i++) {
//            String note = notes[i];
//            TextView textNote = new TextView(getContext());
//            textNote.setText(note);
//            textNote.setTextSize(30);
//            layoutView.addView(textNote);
//            final int fi = i;
//            textNote.setOnClickListener(v -> {
//                currentNote = new Notes(fi, getResources().getStringArray(R.array.notes)[fi]);
//                showDescription(currentNote);
//            });
//        }
//    }

    private void showDescription(Notes currentNote) {
        if (isLandscape) {
            showLandNotes(currentNote);
        } else {
            sshowPortNotes(currentNote);
        }
    }

//    private void showPortNotes(Notes currentNote) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), DescriptionActivity.class);
//        intent.putExtra(DescriptionFragment.ARG_NOTE, currentNote);
//        startActivity(intent);
//    }

    private void showPortNotes(Notes currentNote) {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes, descriptionFragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void sshowPortNotes(Notes currentNote) {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.text_description);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && fragment instanceof NotesFragment) {
            fragmentManager.popBackStack();
        } else {
            fragmentTransaction.replace(R.id.notes, descriptionFragment, descriptionFragment.ARG_NOTE);
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    private void showLandNotes(Notes currentNote) {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.text, descriptionFragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}