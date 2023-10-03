package com.hungnmse160060.prm392_lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMain = findViewById(R.id.btn1);
        btnMain.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("DuLieu","Noi Dung Chuoi");
            startActivity(intent);
        });
    }
}