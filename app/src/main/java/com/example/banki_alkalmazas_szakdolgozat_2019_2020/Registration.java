package com.example.banki_alkalmazas_szakdolgozat_2019_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends AppCompatActivity {
    private EditText etVnev,etKnev,etEmail,etJelszo;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registration.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init(){
        etVnev=findViewById(R.id.etLastName);
        etKnev=findViewById(R.id.etFirstName);
        etEmail=findViewById(R.id.etEmailCim);
        etJelszo=findViewById(R.id.etJelszo);
        btnReg=findViewById(R.id.btnRegisztráció);
    }
}
