package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.content.Intent;
import android.net.Uri;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Transaction extends AppCompatActivity {
    private TextView tvEgyenleg, tvKartyaSzam;
    private Button btnPKuldes, btnPFogadKer, btnPnValtas, btnVissza, btnQRCodeScan;
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
                if (dataSnapshot.exists()) {
                    Tagok tagok = dataSnapshot.getValue(Tagok.class);
                    int egyenleg = 0;

                    egyenleg = tagok.getEgyenleg();
                    tvEgyenleg.setText("Aktuális egyenleg: \n\t " + egyenleg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Felhasználók").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Tagok tagok = dataSnapshot.getValue(Tagok.class);
                    String kartyaszam = "";

                    kartyaszam = tagok.getKartyaszam();
                    tvKartyaSzam.setText("Aktuális kártyaszám: \n\t " + kartyaszam);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transaction.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        btnQRCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator=new IntentIntegrator(Transaction.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("QRCode Scanning by Banki alkalmazás");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });


       /* btnPKuldes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("Felhasználók").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Tagok tagok = dataSnapshot.getValue(Tagok.class);
                            int egyenleg = tagok.getEgyenleg();
                            egyenleg -= 1000;
                            tagok.setEgyenleg();
                            databaseReference.child("Felhasználók").child(firebaseAuth.getUid()).setValue(tagok);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Kiléptünk a scannelésből", Toast.LENGTH_SHORT).show();
            }else
            {

                Uri uri = Uri.parse(result.getContents());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void init() {
        tvEgyenleg = findViewById(R.id.tvEgyenleg);
        tvKartyaSzam = findViewById(R.id.tvCardNumber);
        btnPKuldes = findViewById(R.id.btnMoneySend);
        btnPFogadKer = findViewById(R.id.btnMoneyRequest);
        btnPnValtas = findViewById(R.id.btnCurrencyChange);
        btnQRCodeScan=findViewById(R.id.btnQRCodeScanner);
        btnVissza = findViewById(R.id.btnVissza);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void onBackPressed() {
        Intent intent = new Intent(Transaction.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}
