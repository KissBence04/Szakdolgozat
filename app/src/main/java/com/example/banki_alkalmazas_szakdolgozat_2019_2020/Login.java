package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mindrot.jbcrypt.BCrypt;

public class Login extends AppCompatActivity {
    private EditText etEmail, etJelszo;
    private Button btnReg, btnBejel, btnElfJelszo, btnRating;
    private RatingBar ratingBar;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

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
                if (etEmail.getText().toString().isEmpty() || etJelszo.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
                    return;
                }
                reference.child("Felhasználók").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Tagok tagok = dataSnapshot.getValue(Tagok.class);
                            String jelszo = tagok.getJelszo();
                            if (BCrypt.checkpw(etJelszo.getText().toString(), jelszo)) {

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
                            }else{
                                Toast.makeText(Login.this, "Hibás felhasználónév vagy jelszó", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Köszönjük az értékelését! Értékelése: " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init() {
        etEmail = findViewById(R.id.etEmailAddress);
        etJelszo = findViewById(R.id.etPassword);
        btnBejel = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnRegistration);
        btnElfJelszo = findViewById(R.id.btnForgotPassword);
        btnRating = findViewById(R.id.btnRating);
        ratingBar = findViewById(R.id.rbStars);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public void onBackPressed() {
        alertDialogBuilder = new AlertDialog.Builder(Login.this);
        alertDialogBuilder.setMessage("Biztos ki akarsz lépni az alkalmazásból?");
        alertDialogBuilder.setPositiveButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.setNegativeButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
