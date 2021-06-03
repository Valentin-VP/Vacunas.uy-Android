package com.example.vacunasuy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ConstanciaActivity extends AppCompatActivity {

    private String constancia;
    private String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constancia);

        //obtengo las variables que me manda el intent
        constancia = getIntent().getStringExtra("constancia");
        cookie = getIntent().getStringExtra("cookie");
    }
}