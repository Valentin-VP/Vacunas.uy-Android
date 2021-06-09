package com.example.vacunasuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);//obtengo el sharedPreference
        String cookie = sharedPref.getString("cookie", null);//obtengo la cookie, si no existe retorna null
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(cookie);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        if (cookie == null) {
            setContentView(R.layout.activity_main); //seteo el xml
            Button loginButton = findViewById(R.id.login);
            loginButton.setOnClickListener(new View.OnClickListener() { //seteo el boton
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2:8080/grupo15-services/login?tipoUsuario=ciudadano"));
                    startActivity(browserIntent);
                    System.out.println("funciona");
                }
            });
        } else {
            Intent menu = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(menu);
            System.out.println("entro sin pasar por la cookie");
        }
    }
}