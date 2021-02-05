package com.example.notes;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CardsNoteImpl implements CardsNote, Parcelable {
    private List<Notes> notes;
    private Resources resources;

    public CardsNoteImpl(Resources resources) {
        notes = new ArrayList<>(3);
        this.resources = resources;
    }

    protected CardsNoteImpl(Parcel in) {
        notes = in.createTypedArrayList(Notes.CREATOR);
    }

    public static final Creator<CardsNoteImpl> CREATOR = new Creator<CardsNoteImpl>() {
        @Override
        public CardsNoteImpl createFromParcel(Parcel in) {
            return new CardsNoteImpl(in);
        }

        @Override
        public CardsNoteImpl[] newArray(int size) {
            return new CardsNoteImpl[size];
        }
    };

    public CardsNoteImpl init() {
        String[] titles = resources.getStringArray(R.array.notes);
        for (int i = 0; i < titles.length; i++) {
            notes.add(new Notes(titles[i], i, false));
        }
        return this;
    }

    @Override
    public Notes getNotes(int indexNote) {
        return notes.get(indexNote);
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(notes);
    }
}
