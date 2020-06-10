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

public class LoginMain extends AppCompatActivity {

    Button callLogin_student, callLogin_teacher;
    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_main);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        callLogin_student = findViewById(R.id.login_student);
        callLogin_teacher = findViewById(R.id.login_teacher);

        image = findViewById(R.id.logo);
        text = findViewById(R.id.slogan);

        callLogin_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMain.this, StudentLogin.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "xmum_logo_transition");
                pairs[1] = new Pair<View, String>(text, "xmum_slogan_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginMain.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });

        callLogin_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMain.this, TeacherLogin.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "xmum_logo_transition");
                pairs[1] = new Pair<View, String>(text, "xmum_slogan_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginMain.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }
}