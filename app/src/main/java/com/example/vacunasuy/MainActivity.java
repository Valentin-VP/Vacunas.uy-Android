package com.example.vacunasuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private String clientId = "890192";
    private String clientSecret = "457d52f181bf11804a3365b49ae4d29a2e03bbabe74997a2f510b179";
    private String redirectUri = "https://openidconnect.net/callback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2:8080/grupo15-services/login"));
                startActivity(browserIntent);
                System.out.println("funciona");
            }
        });
    }

}