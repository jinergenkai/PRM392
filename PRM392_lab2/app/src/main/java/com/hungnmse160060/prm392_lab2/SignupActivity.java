package com.hungnmse160060.prm392_lab2;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvNoAccount;
    private Button btnSignin;
    private EditText etConfirm;

    private final String REQUIRE = "Require";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirm = (EditText) findViewById(R.id.etConfirm);
        tvNoAccount = (TextView) findViewById(R.id.tvSignin);
        btnSignin = (Button) findViewById(R.id.btnSignup);
        tvNoAccount.setOnClickListener(this);
        btnSignin.setOnClickListener(this);

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            Intent nextPage = new Intent(this, MainActivity.class);
            startActivity(nextPage);
        });

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
        if (TextUtils.isEmpty(etConfirm.getText().toString())) {
            etConfirm.setError(REQUIRE);
            res = false;
        }
        if (!TextUtils.equals(etPassword.getText().toString(), etConfirm.getText().toString())) {
            Toast.makeText(this, "Password are not match", Toast.LENGTH_LONG).show();
            res = false;
        }
        return res;
    }
    private void signup (){
        if (!checkInput()) {
            return;
        }
    }
    private void signinForm() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignup) {
            signup();
        }
        if (view.getId() == R.id.tvSignin) {
            signinForm();
        }
    }
}
