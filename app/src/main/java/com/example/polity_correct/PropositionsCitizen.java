package com.example.polity_correct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class PropositionsCitizen extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propositions_citizen);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
        titles = new ArrayList<>();
        titles.add("חוקים שעוד לא הוצבעו");
        titles.add("חוקים שהוצבעו בכנסת");

        setViewPagerAdapter();
        new TabLayoutMediator(tabLayout, viewPager2, this).attach();
    }

    public void setViewPagerAdapter() {
        ViewPagerAdapter viewPager2Adapter = new ViewPagerAdapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>(); //creates an ArrayList of Fragments
        fragmentList.add(new PropositionsNotVoted());
        fragmentList.add(new PropositionsVoted());
        viewPager2Adapter.setData(fragmentList); //sets the data for the adapter
        viewPager2.setAdapter(viewPager2Adapter);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }

}