package com.bugapocalypse.srilankarailwaydigital.data;

public class Station {

    final static String collection = "Stations";

    private String id;
    private String name;

    public Station() {
    }

    public Station(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getCollection() {
        return collection;
    }
}
