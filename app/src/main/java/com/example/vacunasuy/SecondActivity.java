package com.example.vacunasuy;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;


import com.android.volley.NetworkResponse;

import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.net.URL;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class SecondActivity extends AppCompatActivity {
    //esta clase se va a crearse cuando se llegue a un http://localhost

    private String cookie;
    private TextView cedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        cedula = (TextView) findViewById(R.id.cedula);

        Uri URIdata = getIntent().getData();
        if (URIdata != null) {


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
                    cookie = response.header("Set-Cookie");
                    System.out.println(cookie);
                    getNombre();
                }
            });


//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);

//        finish();

        }else {//si no obtengo el url capturado termino la actividad y vuevlo al main
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    public void getNombre(){
        String url = "http://10.0.2.2:8080/grupo15-services/rest/mobile/id";
        OkHttpClient client = new OkHttpClient();
        //defino el request
        Request request = new Request.Builder()
                .addHeader("Cookie", cookie)
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
                if(response.body() != null) {
                    String ci = Objects.requireNonNull(response.body()).string();
                    System.out.println(ci);
                    cedula.setText(ci);
                }else{
                    System.out.println("Response.body es null");
                    //manejar que no existe el usuario
                }
            }
        });
    }


}

