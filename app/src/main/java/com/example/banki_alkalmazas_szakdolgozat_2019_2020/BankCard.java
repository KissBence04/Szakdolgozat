package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class BankCard extends AppCompatActivity {
    private ImageView ivKartya;
    private Button btnKartya,btnTranzA,btnEgyenleg,btnVissza;

    private SharedPreferences preferences;

    private DatabaseReference mdatabase;
    private FirebaseAuth auth;
    private FirebaseDatabase mfirebaseDatabase;
    private String userId;
    private static final String TAG="ViewDatabase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);

        init();

        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BankCard.this,HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        btnTranzA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BankCard.this,Transaction.class);
                startActivity(intent);
                finish();
            }
        });

        btnKartya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivKartya.setImageResource(R.drawable.bank_card);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("Virtuális kártya",true);
                editor.apply();
                editor.commit();
            }
        });

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

    public void init()
    {
        ivKartya=findViewById(R.id.ivBankCard);
        btnKartya=findViewById(R.id.btnKartya);
        btnTranzA=findViewById(R.id.btnTranzA);
        btnEgyenleg=findViewById(R.id.btnBalances);
        btnVissza=findViewById(R.id.btnBack);

        preferences=getPreferences(Context.MODE_PRIVATE);
        if(preferences.getBoolean("Virtuális kártya",false))
        {
            ivKartya.setImageResource(R.drawable.bank_card);
        }

        auth=FirebaseAuth.getInstance();
        mfirebaseDatabase=FirebaseDatabase.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        mdatabase= FirebaseDatabase.getInstance().getReference();

    }

    private void showData(){
        mdatabase.child("Felhasználók").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Tagok tagok = dataSnapshot.getValue(Tagok.class);
                    int egyenleg = 0;

                    egyenleg = tagok.getEgyenleg();
                    Toast.makeText(BankCard.this, "Aktuális egyenlege:" + egyenleg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    }
