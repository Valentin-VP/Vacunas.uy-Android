package com.example.vacunasuy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    SharedPreferences sharedPref;
    boolean loggeado = false;
    String endpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);//obtengo el sharedPreference
        String cookie = sharedPref.getString("cookie", null);//obtengo la cookie, si no existe retorna null
        sharedPref.edit().putString("endpoint", "https://grupo15-vacunasuy-testing.web.elasticloud.uy").commit(); //seteo el endpoint a donde quiero que vaya
        //https://grupo15-vacunasuy-testing.web.elasticloud.uy
        //http://10.0.2.2:8080
        endpoint = sharedPref.getString("endpoint", "");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(cookie);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        if(cookie != null) {
            String url = endpoint +"/grupo15-services/rest/usuario/checkToken";
            System.out.println("#####################");
            System.out.println(url);
            System.out.println("#####################");
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
                    System.out.println("Error en la conexion");
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException { //si obtengo respuesta
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    System.out.println(response.code());
                    if(response.code() == 200)
                        loggeado = true;

                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    System.out.println(loggeado);
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

                    continuoFlujo();
                }
            });
        }else
            continuoFlujo();
    }

    private void continuoFlujo(){
        if (!this.loggeado) {
            setContentView(R.layout.activity_main); //seteo el xml
            Button loginButton = findViewById(R.id.login);
            loginButton.setOnClickListener(new View.OnClickListener() { //seteo el boton
                @Override
                public void onClick(View v) {
                    System.out.println("#####################");
                    System.out.println(endpoint+"/grupo15-services/login?tipoUsuario=ciudadano");
                    System.out.println("#####################");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(endpoint+"/grupo15-services/login?tipoUsuario=ciudadano"));
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
