package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    private EditText etEmail,etJelszo;
    private Button btnReg,btnBejel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init(){
        etEmail=findViewById(R.id.etEmailAddress);
        etJelszo=findViewById(R.id.etPassword);
        btnBejel=findViewById(R.id.btnLogin);
        btnReg=findViewById(R.id.btnRegistration);
    }
}
