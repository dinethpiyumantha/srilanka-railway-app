package com.bugapocalypse.srilankarailwaydigital.data;

import java.time.LocalDateTime;
import java.util.Date;

public class Cheking {

    private String userId;
    private String time;
    private Date date;
    private String train;
    private String cheacked;
    private String note;
    private String TicketIdcheak;

    public String getCheacked() {
        return cheacked;
    }

    public void setCheacked(String cheacked) {
        this.cheacked = cheacked;
    }

    public String getTicketIdcheak() {
        return TicketIdcheak;
    }

    public void setTicketIdcheak(String ticketIdcheak) {
        TicketIdcheak = ticketIdcheak;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Cheking() {
        cheacked = "1";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }


}
