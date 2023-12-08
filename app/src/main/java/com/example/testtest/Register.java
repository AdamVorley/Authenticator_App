package com.example.testtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText etEmail, etPassword, etName, etPhone;
    Button bRegister;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        etName = findViewById(R.id.editTextName);
        etPhone = findViewById(R.id.editTextPhone);
        progressBar = findViewById(R.id.progressBarRegister);
        bRegister = findViewById(R.id.buttonRegister);

        bRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // input validation
            if (TextUtils.isEmpty(email))
            {
                etEmail.setError("Please enter an email address");
                return;
            }
            if (TextUtils.isEmpty(password))
            {
                etPassword.setError("Please enter a password");
                return;
            }
            if (!email.contains("@"))
            {
                etEmail.setError("Please enter a valid email address");
                return;
            }
            if (password.length() < 6)
            {
                etPassword.setError("Password must be 6 or more characters");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            //register user
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                    else {
                        Toast.makeText(Register.this, "Error - user already registered", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        });
    }

    public void goToLogin(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}