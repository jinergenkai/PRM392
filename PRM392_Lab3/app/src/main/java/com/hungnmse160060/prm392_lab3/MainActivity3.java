package com.hungnmse160060.prm392_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    ListView lvFootball;
    FootballAdapter footballAdapter;
    List<Football> footballList;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button = (FloatingActionButton) findViewById(R.id.changeLab);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        mapping();
        footballAdapter = new FootballAdapter(this, R.layout.football_item, footballList);
        lvFootball.setAdapter(footballAdapter);
    }
    private void mapping() {
        lvFootball = (ListView) findViewById(R.id.lvFootball);
        footballList = new ArrayList<>();
        footballList.add(new Football("Pele", "October 23, 1940", R.drawable.dua_hau, R.drawable.dua_hau));
        footballList.add(new Football("Diego Maradona", "October 30, 1960", R.drawable.dua_hau, R.drawable.dua_hau));
        footballList.add(new Football("Johan Cruyff", "April 25, 1947", R.drawable.dua_hau, R.drawable.dua_hau));
        footballList.add(new Football("Franz Beckenbauer", "September 11, 1945", R.drawable.dua_hau, R.drawable.dua_hau));
        footballList.add(new Football("Michel Platini", "June 21, 1955", R.drawable.dua_hau, R.drawable.dua_hau));
        footballList.add(new Football("Rolnaldo De Lima", "September 22, 1976", R.drawable.dua_hau, R.drawable.dua_hau));
    }
}