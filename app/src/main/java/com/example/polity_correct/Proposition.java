package com.example.polity_correct;

import java.util.HashMap;

public class Proposition {
    private String key;
    private String title;
    private int status;
    private String description;
    private Category category;
    private HashMap<Integer,StatusVote> curr_votes;
    private HashMap<String, StatusVote> mp_results;

    public Proposition(String key, String title, int status, String description, Category category,
                       HashMap<Integer, StatusVote> curr_votes, HashMap<String, StatusVote> mp_results) {
        this.key = key;
        this.title = title;
        this.status = status;
        this.description = description;
        this.category = category;
        this.curr_votes = curr_votes;
        this.mp_results = mp_results;
    }

    public void update_status(int status){ this.status= status;}

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, StatusVote> getMp_results() {
        return mp_results;
    }

    public Category getCategory() {
        return category;
    }
}

enum Category{
    Social,
    Economy
}