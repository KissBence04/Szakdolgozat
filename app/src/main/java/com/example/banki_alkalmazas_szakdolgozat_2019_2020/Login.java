package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText etEmail, etJelszo;
    private Button btnReg, btnBejel, btnElfJelszo;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });

        btnBejel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().isEmpty()
                        || etJelszo.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etJelszo.getText().toString())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (!user.isEmailVerified()) {
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(Login.this, "Erősítsd meg az emailedet", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            Intent intent = new Intent(Login.this, HomePage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(Login.this, "Hibás felhasználónév vagy jelszó", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        btnElfJelszo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotUserPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init() {
        etEmail = findViewById(R.id.etEmailAddress);
        etJelszo = findViewById(R.id.etPassword);
        btnBejel = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnRegistration);
        btnElfJelszo = findViewById(R.id.btnForgotPassword);

        mAuth = FirebaseAuth.getInstance();
    }
}
