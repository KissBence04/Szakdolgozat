package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

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

public class Transaction extends AppCompatActivity {
    private TextView tvEgyenleg;
    private Button btnPKuldes,btnPFogadKer, btnPnValtas,btnVissza;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        init();

        databaseReference.child("Felhasználók").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Tagok tagok = dataSnapshot.getValue(Tagok.class);
                    int egyenleg = 0;

                    egyenleg = tagok.getEgyenleg();
                    tvEgyenleg.setText("Aktuális egyenleg: "+egyenleg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Transaction.this,HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init(){
        tvEgyenleg=findViewById(R.id.tvEgyenleg);
        btnPKuldes=findViewById(R.id.btnMoneySend);
        btnPFogadKer=findViewById(R.id.btnMoneyRequest);
        btnPnValtas=findViewById(R.id.btnCurrencyChange);
        btnVissza=findViewById(R.id.btnVissza);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }
}
