package com.example.vacunasuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ComeBackActivity extends AppCompatActivity {

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_come_back);

        // get or create SharedPreferences
        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);

        Uri URIdata = getIntent().getData();
        if(URIdata != null ) {
            String uriString = URIdata.toString();
            String url = uriString.replaceAll("localhost/", "10.0.2.2:8080/grupo15-services/callback"); //cambio el link para que se direccione a donde quiero

            //defino el cliente para hacer el http request
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .followRedirects(false)
                    .build();
            //defino el request
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            //hago el http request
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) { //si falla
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException { //si obtengo respuesta
                    String cookie = response.header("Set-Cookie");

                    if (cookie.contains("x-access-token")) {//si el usuario esta registrado en la bd nos retorna un x-access-token
                        System.out.println("#########################################");
                        System.out.println(cookie);
                        // guardar cookie en SharedPreferences
                        sharedPref.edit().putString("cookie", cookie).commit();
                        System.out.println("#########################################");
                        goMenu(cookie);
                        //getNombre();
                    } else { //si el usuario no esta registrado en la bd no retorna el x-access-token y lo mando a registrarse
                        Intent main = new Intent(ComeBackActivity.this, Register.class);
                        startActivity(main);
                    }
                }
            });


        }else{
            finish();
        }
    }

    public void goMenu(String cookie){
        Intent menu = new Intent(ComeBackActivity.this, SecondActivity.class);
        startActivity(menu);
    }
}