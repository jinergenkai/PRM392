package com.hungnmse160060.prm392_lab2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvNoAccount;
    private Button btnSignin;

    private final String REQUIRE = "Require";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etUsername = (EditText) findViewById(R.id.etUsername1);
        etPassword = (EditText) findViewById(R.id.etPassword1);
        tvNoAccount = (TextView) findViewById(R.id.tvNoAccount);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        tvNoAccount.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
    }
    private boolean checkInput() {
        boolean res = true;
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(REQUIRE);
            res = false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(REQUIRE);
            res = false;
        }
        return res;
    }
    private void signin (){
        if (!checkInput()) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void signupForm() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignin) {
            signin();
        }
        if (view.getId() == R.id.tvNoAccount) {
            signupForm();
        }
    }
}
