package com.example.polity_correct;

import java.util.HashMap;

public class Proposition {
    private String key;
    private String title;
    private String description;
    private Category category;
    private HashMap<String,StatusVote> curr_votes= new HashMap<>();
    private HashMap<Integer, StatusVote> mp_results= new HashMap<>();

    public Proposition(String key, String title, String description, Category category){
        this.key=key;
        this.title=title;
        this.description=description;
        this.category=category;
    }

    public void update_citizen_votes(int citizen_id, StatusVote sv){
        mp_results.put(citizen_id, sv);
    }

    public void updateMPResult(int mp_id, StatusVote sv){
        mp_results.put(mp_id, sv);
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<Integer, StatusVote> getMp_results() {
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