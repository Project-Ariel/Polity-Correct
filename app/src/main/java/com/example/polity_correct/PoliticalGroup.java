package com.example.polity_correct;

import java.util.Date;
import java.util.Set;

public class PoliticalGroup {
    private String group_key;
    private String group_name;
    private String abbreviation;
    private String group_website;
    private Set<String> citizens;
    private Set<String> parliament;

    public PoliticalGroup(String group_key, String group_name, String abbreviation,
                                String group_website, Set<String> citizens, Set<String> parliament) {
        this.group_key = group_key;
        this.group_name = group_name;
        this.abbreviation = abbreviation;
        this.group_website = group_website;
        this.citizens = citizens;
        this.parliament = parliament;
    }

    public Set<String> getCitizens() {
        return citizens;
    }

    public void setCitizens(Set<String> citizens) {
        this.citizens = citizens;
    }

    public Set<String> getParliament() {
        return parliament;
    }

    public void setParliament(Set<String> parliament) {
        this.parliament = parliament;
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
