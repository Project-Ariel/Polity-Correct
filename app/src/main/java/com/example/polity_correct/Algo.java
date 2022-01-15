package com.example.polity_correct;

import java.util.ArrayList;

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
}
