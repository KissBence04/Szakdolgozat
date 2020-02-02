package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    private TextView tvWelc;
    private Button btnKartya,btnTranzakcio,btnKilep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        init();

        btnKilep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        btnKartya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,BankCard.class);
                startActivity(intent);
                finish();
            }
        });

        btnTranzakcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,Transaction.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init(){
        tvWelc=findViewById(R.id.tvWelcomeMessage);
        btnKartya=findViewById(R.id.btnCard);
        btnTranzakcio=findViewById(R.id.btnTransaction);
        btnKilep=findViewById(R.id.btnExit);
    }
}
