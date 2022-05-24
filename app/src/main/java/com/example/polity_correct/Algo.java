package com.example.polity_correct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Algo {
    public static double[] calculate_votes_grades(ArrayList<String> votes, ArrayList<Double> grades) {
        double[] res = {0, 0, 0, 0};
        for (String v : votes) {
            int choice;
            switch (v) {
                case "against":
                    choice = 0;
                    break;
                case "abstain":
                    choice = 1;
                    break;
                case "agreement":
                    choice = 2;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v);
            }
            res[choice]++;
        }

        float sum = (float) (res[0] + res[1] + res[2]);
        if (sum != 0) {
            res[0] = ((res[0] / sum) * 100);
            res[1] = ((res[1] / sum) * 100);
            res[2] = ((res[2] / sum) * 100);
        }

        for (Double g : grades) {
            res[3] += g;
        }
        if (res[3] != 0)
            res[3] /= (sum);
        return res;
    }

    //    Before sending this function, we should make sure that we have enough votes...
    /*
    For every parliament member, we would like to compare his or her voting vector with the citizen's voting vector,
    with consideration of citizen's ranking of laws.
    We will use this algorithm of diff between vectors:

        diff= sum( ratings[i]*|vote_citizen[i]-vote_pm[i]| ) / sum( ratings )

    We will take the value (1-diff) cause every grater rating will increase the diff...
     */
    public static LinkedHashMap<String, Integer> matchParliament(int[] votes_user, double[] rating, HashMap<String, int[]> votes_pm) {

        HashMap<String, Integer> percents_result = new HashMap<>();
        double sum_of_ratings = 0;

        for (int r = 0; r < rating.length; r++) {
            rating[r]++;
            sum_of_ratings += rating[r];
        }

        for (Map.Entry<String, int[]> entry : votes_pm.entrySet()) {
            double sum = 0;
            for (int c = 0; c < votes_user.length; c++) {
                if(votes_user[c] == entry.getValue()[c]){
                    sum += rating[c] ;
                }
            }
            percents_result.put(entry.getKey(), (int)(sum * 100/ sum_of_ratings));
        }

        //sort the hash-map for taking the best results.
        return sortValues(percents_result);
    }

    //method to sort values
    private static LinkedHashMap sortValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        //Descending Order
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        //copying the sorted list in HashMap to preserve the iteration order
        LinkedHashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        return sortedHashMap;
    }
}

