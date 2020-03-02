package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Registration extends AppCompatActivity {
    private EditText etVnev, etKnev, etEmail, etJelszo;
    private Button btnReg, bttnVissza;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Tagok tagok;

    private long maxid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etVnev.getText().toString().isEmpty()
                        || etKnev.getText().toString().isEmpty()
                        || etEmail.getText().toString().isEmpty()
                        || etJelszo.getText().toString().isEmpty()) {
                    Toast.makeText(Registration.this, "Minden mezőt ki kell tölteni", Toast.LENGTH_SHORT).show();
                } else {
                    tagok.setFelhasznalonev(etVnev.getText().toString() + " " + etKnev.getText().toString());
                    tagok.setEmail(etEmail.getText().toString());
                    tagok.setJelszo(etJelszo.getText().toString());
                    tagok.setKartyaszam();
                    tagok.setEgyenleg();


                    firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etJelszo.getText().toString())
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etJelszo.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                                        databaseReference.child(firebaseUser.getUid()).setValue(tagok);
                                                        if (!firebaseUser.isEmailVerified()) {
                                                            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(Registration.this, "Erősítsd meg az email címed", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(Registration.this, Login.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            });
                                                        }
                                                    } else {
                                                        Toast.makeText(Registration.this, "Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }
                            });
                }
            }
        });

        bttnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init() {
        etVnev = findViewById(R.id.etLastName);
        etKnev = findViewById(R.id.etFirstName);
        etEmail = findViewById(R.id.etEmailCim);
        etJelszo = findViewById(R.id.etJelszo);
        btnReg = findViewById(R.id.btnRegisztráció);
        bttnVissza = findViewById(R.id.bttnVissza);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Felhasználók");
        firebaseAuth = FirebaseAuth.getInstance();
        tagok = new Tagok();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxid = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
