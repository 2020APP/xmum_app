package com.example.xmum_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    Button callBack, callRegister, callLogin;
    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        callBack = findViewById(R.id.back);
        callRegister = findViewById(R.id.register);
        callLogin = findViewById(R.id.sign_in);

        image = findViewById(R.id.logo);
        text = findViewById(R.id.slogan);

        callBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        callRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, LoginSecondary.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "xmum_logo_transition");
                pairs[1] = new Pair<View, String>(text, "xmum_slogan_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Registration.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }
}