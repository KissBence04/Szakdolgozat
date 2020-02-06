package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BankCard extends AppCompatActivity {
    private ImageView ivKartya;
    private Button btnKartya,btnTranzA,btnEgyenleg,btnVissza;

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
                if (ivKartya.getDrawable() == null) {
                    ivKartya.setImageResource(R.drawable.bank_card);
                }
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
    }
}
