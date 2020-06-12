package com.example.xmum_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    Button callBack, callRegister;
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

        image = findViewById(R.id.logo);
        text = findViewById(R.id.slogan);

        callBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "xmum_logo_transition");
                pairs[1] = new Pair<View, String>(text, "xmum_slogan_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Registration.this, pairs);

                finish();
            }
        });

        callRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}