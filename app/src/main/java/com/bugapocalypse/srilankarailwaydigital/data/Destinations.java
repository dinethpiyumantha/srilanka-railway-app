package com.bugapocalypse.srilankarailwaydigital.data;

public class Destinations {

    final static String collection = "Destinations";

    private String from;
    private String id;
    private String to;
    private String train;

    public Destinations() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public static String getCollection() {
        return collection;
    }
}
