package com.example.vacunasuy;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConstanciaActivity extends AppCompatActivity {

    private String constancia;
    private String cookie;
    TextView id, inmunidad, dosisRecibidas, ultimaDosis, vacuna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constancia);

        id = findViewById(R.id.id);
        inmunidad = findViewById(R.id.inmunidad);
        dosisRecibidas = findViewById(R.id.dosisRecibidas);
        ultimaDosis = findViewById(R.id.ultimaDosis);
        vacuna = findViewById(R.id.vacuna);

        //obtengo las variables que me manda el intent
        id.setText(getIntent().getStringExtra("idConstVac"));
        inmunidad.setText(getIntent().getStringExtra("inmunidad"));
         dosisRecibidas.setText(getIntent().getStringExtra("dosisResibidas"));
        ultimaDosis.setText(getIntent().getStringExtra("ultimaDosis"));
        vacuna.setText(getIntent().getStringExtra("vacuna"));

    }
}