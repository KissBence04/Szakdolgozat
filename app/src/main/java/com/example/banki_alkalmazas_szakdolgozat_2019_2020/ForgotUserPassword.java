package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotUserPassword extends AppCompatActivity {
    private EditText etEmail;
    private Button btnUjJelszo;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_user_password);
        init();

        btnUjJelszo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(etEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotUserPassword.this, "Email el lett küldve", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotUserPassword.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ForgotUserPassword.this, "Email nem lett elküldve", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void init() {
        etEmail = findViewById(R.id.Email);
        btnUjJelszo = findViewById(R.id.bttnElfejtettJelszo);

        firebaseAuth = FirebaseAuth.getInstance();
    }
}
