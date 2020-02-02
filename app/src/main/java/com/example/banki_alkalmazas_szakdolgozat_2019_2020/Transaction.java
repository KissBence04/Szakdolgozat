package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Transaction extends AppCompatActivity {
    private TextView tvEgyenleg;
    private Button btnPKuldes,btnPFogadKer, btnPnValtas,btnVissza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        init();

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
    }
}
