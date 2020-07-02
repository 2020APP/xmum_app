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

public class LoginSecondary extends AppCompatActivity {
    Button callSign_in, callSign_up, callForget_password, callBack;
    ImageView image;
    TextView text;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_login_secondary);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);


        callSign_in = findViewById(R.id.sign_in);
        callSign_up = findViewById(R.id.create_account);
        callForget_password = findViewById(R.id.forget_password);
        callBack = findViewById(R.id.back);

        image = findViewById(R.id.logo);
        text = findViewById(R.id.slogan);

        callSign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        callSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSecondary.this, Registration.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "xmum_logo_transition");
                pairs[1] = new Pair<View, String>(text, "xmum_slogan_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginSecondary.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });

        callForget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSecondary.this, recoverPassword.class);
                startActivity(intent);
            }
        });

        callBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}