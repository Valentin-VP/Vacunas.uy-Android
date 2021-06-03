package com.example.vacunasuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ComeBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_come_back);

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
                    System.out.println("#########################################");
                    e.printStackTrace();
                    System.out.println("#########################################");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException { //si obtengo respuesta
                    String cookie = response.header("Set-Cookie");

                    if (cookie.contains("x-access-token")) {//si el usuario esta registrado en la bd nos retorna un x-access-token
                        System.out.println("#########################################");
                        System.out.println(cookie);
                        System.out.println("#########################################");
                        goMenu(cookie);
                        //getNombre();
                    } else { //si el usuario no esta registrado en la bd no retorna el x-access-token y lo mando a registrarse
                        Intent main = new Intent(ComeBackActivity.this, MainActivity.class);
                        startActivity(main);
                    }
                }
            });


        }else{
            finish();
        }
    }

    public void goMenu(String cookie){
        ((CookieClass) ComeBackActivity.this.getApplication()).setCookie(cookie);
        Intent menu = new Intent(ComeBackActivity.this, SecondActivity.class);
        startActivity(menu);
    }
}