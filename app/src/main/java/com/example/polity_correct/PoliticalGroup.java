package com.example.polity_correct;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PoliticalGroup {
    private String group_key;
    private String group_name;
    private String abbreviation;
    private String group_website;
    private Set<String> citizens= new HashSet<>();
    private Set<String> parliaments= new HashSet<>();;

    public PoliticalGroup(String group_key, String group_name, String abbreviation,
                                String group_website) {
        this.group_key = group_key;
        this.group_name = group_name;
        this.abbreviation = abbreviation;
        this.group_website = group_website;
    }

    public Set<String> getCitizens() {
        return citizens;
    }

    public void addCitizens(String citizen) {
        this.citizens.add(citizen);
    }

    public Set<String> getParliament() {
        return parliaments;
    }

    public void addParliament(String parliament) {
        this.parliaments.add(parliament);
    }

    public String getGroup_key() {
        return group_key;
    }

    public void setGroup_key(String group_key) {
        this.group_key = group_key;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getGroup_website() {
        return group_website;
    }

    public void setGroup_website(String group_website) {
        this.group_website = group_website;
    }
}
