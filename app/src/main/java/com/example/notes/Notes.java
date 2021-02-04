package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {
    private String title;
    private String description;
    private int indexNote;
    private boolean like;

    public Notes(String title, int indexNote, boolean like) {
        this.title = title;
        this.indexNote = indexNote;
        this.like = like;
    }

    public Notes(int indexNote, String description) {
        this.indexNote = indexNote;
        this.description = description;
    }

    protected Notes(Parcel in) {
        title = in.readString();
        description = in.readString();
        indexNote = in.readInt();
        like = in.readByte() != 0;
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

    public int getIndexNote() {
        return indexNote;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLike() {
        return like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(indexNote);
        dest.writeByte((byte) (like ? 1 : 0));
    }
}
