package com.example.polity_correct;

import java.util.Comparator;

public class UserVote {
    String rule_id; // rule id
    int vote = -1;
    Double rate = -1.;
    Boolean accepted; //proposition accepted already

    public UserVote(String rule_id) {
        this.rule_id = rule_id;
    }

    public UserVote(String rule_id, String vote, Double rate) {
        this.rule_id = rule_id;
        if (vote.equals("abstain"))
            this.vote = 1;
        else if (vote.equals("against"))
            this.vote = 0;
        else
            this.vote = 2;
        this.rate = rate;
    }

    public String getRule_id() {
        return rule_id;
    }

    public void setRule_id(String rule_id) {
        this.rule_id = rule_id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(String vote) {
        if (vote.equals("abstain"))
            this.vote = 1;
        else if (vote.equals("against"))
            this.vote = 0;
        else
            this.vote = 2;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "rule_id='" + rule_id + '\'' +
                ", vote=" + vote +
                ", rate=" + rate +
                ", accepted=" + accepted +
                '}';
    }
}

class SortByGrade implements Comparator<UserVote> {

    // Method
    // Sorting in ascending order of roll number
    public int compare(UserVote a, UserVote b) {
        if (a.rate == b.rate)
            return 0;
        else if (a.rate < b.rate)
            return 1;
        else
            return -1;
    }
}

