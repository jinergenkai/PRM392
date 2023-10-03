package com.hungnmse160060.prm392_lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtData = findViewById(R.id.txtData);

        Intent intent = getIntent();
        txtData.setText(intent.getStringExtra("DuLieu"));
    }
}