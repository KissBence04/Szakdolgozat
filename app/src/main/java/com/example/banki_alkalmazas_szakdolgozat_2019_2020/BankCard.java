package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BankCard extends AppCompatActivity {
    private ImageView ivKartya;
    private Button btnKartya, btnTranzA, btnEgyenleg, btnVissza;
    private TextView textView;

    private SharedPreferences preferences;

    private DatabaseReference mdatabase;
    private FirebaseAuth auth;
    private FirebaseDatabase mfirebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);

        init();


        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankCard.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        btnTranzA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankCard.this, Transaction.class);
                startActivity(intent);
                finish();
            }
        });

        btnKartya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivKartya.setImageResource(R.drawable.bank_card);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("Virtuális kártya", true);
                editor.apply();
                editor.commit();

                mdatabase.child("Felhasználók").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            SharedPreferences.Editor editor1=preferences.edit();
                            Tagok tagok = dataSnapshot.getValue(Tagok.class);
                            String kartyaszam = "";

                            kartyaszam = tagok.getKartyaszam();
                            editor1.putString("kartyaszam",kartyaszam);
                            editor1.apply();
                            editor1.commit();
                            textView.setText("\tKártyaszám: " + kartyaszam);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        /*mdatabase.child("Felhasználók").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Tagok tagok = dataSnapshot.getValue(Tagok.class);
                    String kartyaszam = "";

                    kartyaszam = tagok.getKartyaszam();
                    textView.setText("Kártyaszám: " + kartyaszam);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        btnEgyenleg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        showData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void init() {
        ivKartya = findViewById(R.id.ivBankCard);
        textView = findViewById(R.id.tvKartyaSzam);
        btnKartya = findViewById(R.id.btnKartya);
        btnTranzA = findViewById(R.id.btnTranzA);
        btnEgyenleg = findViewById(R.id.btnBalances);
        btnVissza = findViewById(R.id.btnBack);

        preferences = getPreferences(Context.MODE_PRIVATE);
        if (preferences.getBoolean("Virtuális kártya", false)) {
            ivKartya.setImageResource(R.drawable.bank_card);
        }
        String kartyaszam = preferences.getString("kartyaszam", "");
        if (!kartyaszam.equals("")){
            textView.setText("\tKártyaszám: " + kartyaszam);
        }

        auth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        mdatabase = FirebaseDatabase.getInstance().getReference();

    }

    private void showData() {
        mdatabase.child("Felhasználók").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Tagok tagok = dataSnapshot.getValue(Tagok.class);
                    int egyenleg = 0;

                    egyenleg = tagok.getEgyenleg();
                    Toast.makeText(BankCard.this, "Aktuális egyenlege: " + egyenleg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(BankCard.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}
