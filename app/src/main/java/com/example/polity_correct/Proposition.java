package com.example.polity_correct;

import java.io.Serializable;

public class Proposition implements Serializable {
    private String key;
    private String title;
    private String status;
    private String description;
    private String category;
    private boolean voted;


    public Proposition(String key, String title, String status, String description, String category,
                       boolean voted) {
        this.key = key;
        this.title = title;
        this.status = status;
        this.description = description;
        this.category = category;
        this.voted = voted;
    }

    public void update_status(String status){ this.status= status;}

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean wasVoted() {
        return voted;
    }
}