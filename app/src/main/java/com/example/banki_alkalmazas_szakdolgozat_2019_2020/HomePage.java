package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;

public class HomePage extends AppCompatActivity {
    private TextView tvWelc;
    private Button btnKartya, btnTranzakcio, btnKilep, btnKijelentkezes;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private Tagok tagok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        init();

        databaseReference.child("Felhasználók").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Tagok tagok = dataSnapshot.getValue(Tagok.class);
                    String fnev = "";

                    fnev = tagok.getFelhasznalonev();
                    tvWelc.setText("Üdvözüljük " + fnev + " alkalmazásunkban!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnKilep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder = new AlertDialog.Builder(HomePage.this);
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
        });

        btnKartya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, BankCard.class);
                startActivity(intent);
                finish();
            }
        });

        btnTranzakcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Transaction.class);
                startActivity(intent);
                finish();
            }
        });

        btnKijelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(HomePage.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init() {
        tvWelc = findViewById(R.id.tvWelcomeMessage);
        btnKartya = findViewById(R.id.btnCard);
        btnTranzakcio = findViewById(R.id.btnTransaction);
        btnKilep = findViewById(R.id.btnExit);
        btnKijelentkezes = findViewById(R.id.btnSignOut);
        tagok = new Tagok();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void onBackPressed() {
        alertDialogBuilder = new AlertDialog.Builder(HomePage.this);
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
