package com.example.polity_correct;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class AlgoTest {

    ArrayList<String> votes;
    ArrayList<Double> grades;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calculate_votes_grades_empty_votes() {
        //Test1 - empty votes
        votes = new ArrayList<String>(Collections.emptyList());
        grades = new ArrayList<Double>(Collections.emptyList());

        double[] realRes = {0, 0, 0, 0};
        double[] algoRes = Algo.calculate_votes_grades(votes, grades);

        assertArrayEquals("calculate_votes_grades - empty votes", realRes, algoRes, 0.0);
    }

    @Test
    public void calculate_votes_grades_normal_votes() {
        //Test2 - normal votes
        votes = new ArrayList<String>(Arrays.asList("against", "abstain", "abstain", "agreement", "agreement"));
        grades = new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));

        double[] realRes = {20.0, 40.0, 40.0, 3.0};
        double[] algoRes = Algo.calculate_votes_grades(votes, grades);

        assertArrayEquals("calculate_votes_grades - normal votes", realRes, algoRes, 0.0);
    }

    @Test
    public void calculate_votes_grades_same_opinion() {
        //Test3 - same_opinion
        votes = new ArrayList<String>(Arrays.asList("against", "against", "against", "against", "against"));
        grades = new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));

        double[] realRes = {100.0, 0.0, 00.0, 3.0};
        double[] algoRes = Algo.calculate_votes_grades(votes, grades);

        assertArrayEquals("calculate_votes_grades - same_opinion", realRes, algoRes, 0.0);
    }
}