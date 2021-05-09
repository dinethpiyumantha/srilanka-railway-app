package com.bugapocalypse.srilankarailwaydigital.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Ticket implements Parcelable {

    // Declaration of properties
    private String id;
    private String userId;
    private String from;
    private String to;
    private String date;
    private String time;
    private String trclass;
    private String qty;
    private String train;
    private String price;
    private String approve;
    private String note;

    // Constructor
    public Ticket() {
        approve = "0";
        note = " ";
    }

    // Constructor for create a parcel
    protected Ticket(Parcel in) {
        id = in.readString();
        userId = in.readString();
        from = in.readString();
        to = in.readString();
        date = in.readString();
        time = in.readString();
        trclass = in.readString();
        qty = in.readString();
        train = in.readString();
        price = in.readString();
        approve = in.readString();
        note = in.readString();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    // Getters and Setters
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTrclass() {
        return trclass;
    }

    public void setTrclass(String trclass) {
        this.trclass = trclass;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(trclass);
        dest.writeString(qty);
        dest.writeString(train);
        dest.writeString(price);
        dest.writeString(approve);
        dest.writeString(note);
    }
}
