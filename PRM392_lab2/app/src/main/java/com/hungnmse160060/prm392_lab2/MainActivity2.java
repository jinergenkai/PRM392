package com.hungnmse160060.prm392_lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{
    EditText etFirst;
    EditText etSecond;
    Button btnPlus;
    Button btnMinus;
    Button btnMul;
    Button btnDiv;
    TextView txtResult;

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etFirst = (EditText) findViewById(R.id.etFirstNumber);
        etSecond = (EditText) findViewById(R.id.etSecondNumber);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMul = (Button) findViewById(R.id.btnMulti);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            Intent nextPage = new Intent(this, SigninActivity.class);
            startActivity(nextPage);
        });

    }

    @Override
    public void onClick(View view) {
        try {
            int firstNum = Integer.parseInt(etFirst.getText().toString());
            int secondNum = Integer.parseInt(etSecond.getText().toString());
            if (view.getId() == R.id.btnPlus) {
                txtResult.setText(firstNum + secondNum + "");
            }
            if (view.getId() == R.id.btnMinus) {
                txtResult.setText(firstNum - secondNum + "");
            }
            if (view.getId() == R.id.btnMulti) {
                txtResult.setText(firstNum * secondNum + "");
            }
            if (view.getId() == R.id.btnDiv) {
                txtResult.setText((float)firstNum / (float)secondNum + "");
            }
        }
        catch (Exception ex) {
            txtResult.setText(ex.getMessage());
        }
    }
}