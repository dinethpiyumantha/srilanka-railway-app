package com.bugapocalypse.srilankarailwaydigital.data;

public class Train {

    final static String collection = "Trains";

    private String id;
    private String name;

    public Train() {
    }

    public Train(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getCollection() {
        return collection;
    }
}
