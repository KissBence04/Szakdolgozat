package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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

public class BankCard extends AppCompatActivity {
    private ImageView ivKartya;
    private Button btnKartya,btnTranzA,btnEgyenleg,btnVissza;

    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;

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

            }
        });

       /* btnEgyenleg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       String egyenleg=dataSnapshot.getValue("Felhasználók/Uid/egyenleg");
                       Log.d(dataSnapshot.getKey(), "Egyenleg: "+egyenleg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/
    }

    public void init()
    {
        ivKartya=findViewById(R.id.ivBankCard);
        btnKartya=findViewById(R.id.btnKartya);
        btnTranzA=findViewById(R.id.btnTranzA);
        btnEgyenleg=findViewById(R.id.btnBalances);
        btnVissza=findViewById(R.id.btnBack);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Felhasználók/Uid/egyenleg");
    }
}
