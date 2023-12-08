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

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button bLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.editTextEmailSignin);
        etPassword = findViewById(R.id.editTextPasswordSignin);
        bLogin = findViewById(R.id.buttonSignIn);
        progressBar = findViewById(R.id.progressBarSignIn);

        fAuth = FirebaseAuth.getInstance();

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // input validation
                if (TextUtils.isEmpty(email))
                {
                    etEmail.setError("Please enter an email address");
                    return;
                }
                if (!email.contains("@"))
                {
                    etEmail.setError("Please enter a valid email address");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    etPassword.setError("Please enter a password");
                    return;
                }
                if (password.length() < 6)
                {
                    etPassword.setError("Password must be 6 or more characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else  {
                            Toast.makeText(Login.this, "Error - User not found", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });


    }

    public void goToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(), Register.class));
    }
}