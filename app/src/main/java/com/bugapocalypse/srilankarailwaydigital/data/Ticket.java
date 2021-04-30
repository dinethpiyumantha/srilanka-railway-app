package com.bugapocalypse.srilankarailwaydigital.data;

public class Ticket {

    private String id;
    private String userId;
    private String from;
    private String to;
    private String date;
    private String trclass;
    private String train;

    public Ticket() {
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
}
