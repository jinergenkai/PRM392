package com.hungnmse160060.lab1_linear_relative_contraint_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener((view, motionEvent) -> {
            view.performClick();
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                Intent intent = new Intent(this, MainActivity4.class);
                startActivity(intent);
            }
            return true;
        });
    }
}