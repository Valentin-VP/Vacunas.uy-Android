package com.example.vacunasuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        System.out.println("Entro a SecondActivity");

        Uri URIdata = getIntent().getData();
        if(URIdata != null) {
            System.out.println("llega a la segunda actividad");


            String uriString = URIdata.toString();
            String url = uriString.replaceAll("localhost/", "10.0.2.2:8080/grupo15-services/callback");
            RequestQueue rq = Volley.newRequestQueue(this);
            StringRequest or = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString());
                        }
                    }
            );
            rq.add(or);

            // String jasonWebToken = URIdata.getQueryParameter("jwt");
        }




//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);

//        finish();


    }
}