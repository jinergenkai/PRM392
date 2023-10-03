package com.hungnmse160060.prm392_groupproject01_racingboy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        username.setText("nguoikhongchoilanguoithang");
        password.setText("123456");
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener((view) -> {
                if (username.getText().toString().equals("nguoikhongchoilanguoithang") && password.getText().toString().equals("123456")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }
}