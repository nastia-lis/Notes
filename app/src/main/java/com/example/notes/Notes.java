package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {
    private int indexNote;
    private String description;

    public Notes(int indexNote, String description) {
        this.indexNote = indexNote;
        this.description = description;
    }

    public int getIndexNote() {
        return indexNote;
    }

    public String getDescription() {
        return description;
    }

    protected Notes(Parcel in) {
        indexNote = in.readInt();
        description = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(indexNote);
        dest.writeString(description);
    }
}
