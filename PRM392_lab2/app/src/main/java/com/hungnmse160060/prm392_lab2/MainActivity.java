package com.hungnmse160060.prm392_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText txtMin;
    private EditText txtMax;
    private Button btnGenerate;
    private TextView txtResult;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMin = (EditText) findViewById(R.id.txtMin);
        txtMax = (EditText) findViewById(R.id.txtMax);
        btnGenerate = (Button) findViewById(R.id.btnGenerate);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnGenerate.setOnClickListener((View view) -> {
                    try {
                        int min = Integer.parseInt(txtMin.getText().toString());
                        int max = Integer.parseInt(txtMax.getText().toString());
                        if (min > max) {
                            txtResult.setText("Min value can not be greater than max value");
                            return;
                        }
                        Random random = new Random();
                        int num = min + random.nextInt(max - min + 1);
                        txtResult.setText(String.valueOf(num));
                    } catch (NumberFormatException ex) {
                        txtResult.setText("Input must be number");
                    }
                }
        );

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            Intent nextPage = new Intent(this, MainActivity2.class);
            startActivity(nextPage);
        });

    }
}