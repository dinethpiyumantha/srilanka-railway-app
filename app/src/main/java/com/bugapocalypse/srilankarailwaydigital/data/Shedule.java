package com.bugapocalypse.srilankarailwaydigital.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Shedule implements Parcelable {

    // Declaration of properties
    private String id;
    private String from;
    private String to;
    private String date;
    private String time;

    // Constructor
    public Shedule() {

    }

    // Constructor for create a parcel
    protected Shedule(Parcel in) {
        id = in.readString();
        from = in.readString();
        to = in.readString();
        date = in.readString();
        time = in.readString();

    }

    public static final Creator<Shedule> CREATOR = new Creator<Shedule>() {
        @Override
        public Shedule createFromParcel(Parcel in) {
            return new Shedule(in);
        }

        @Override
        public Shedule[] newArray(int size) {
            return new Shedule[size];
        }
    };

    // Getters and Setters

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(date);
        dest.writeString(time);

    }
}
