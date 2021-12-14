package com.example.polity_correct;

import java.io.Serializable;
import java.util.HashMap;

public class Proposition implements Serializable {
    private String key;
    private String title;
    private String status;
    private String description;
//    private Category category;
    private String category;
    private HashMap<Integer,StatusVote> curr_votes;
    private HashMap<String, StatusVote> mp_results;

    public Proposition(String key, String title, String status, String description, String category,
                       HashMap<Integer, StatusVote> curr_votes, HashMap<String, StatusVote> mp_results) {
        this.key = key;
        this.title = title;
        this.status = status;
        this.description = description;
        this.category = category;
        this.curr_votes = curr_votes;
        this.mp_results = mp_results;
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

    public HashMap<String, StatusVote> getMp_results() {
        return mp_results;
    }

    public String getCategory() {
        return category;
    }
}