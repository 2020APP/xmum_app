package com.example.xmum_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginSecondary extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button callSign_in, callSign_up, callForget_password, callBack;
    ImageView image;
    TextView text;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_secondary);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        spinner = findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}