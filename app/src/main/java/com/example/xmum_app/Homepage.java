package com.example.xmum_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;


public class Homepage extends AppCompatActivity {
    private SessionHandler session;
    private DrawerLayout drawerLayout;
    private TextView mTextMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        final String privilege = user.getRole();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if(itemId == R.id.nav_grade && privilege.equals("Staff")) {
                    replaceFragment(new StaffViewGrade());
                }
                if(itemId == R.id.nav_grade && privilege.equals("Student")) {
                    replaceFragment(new StudentViewGrade());
                }
                if(itemId == R.id.nav_logout) {
                    session.logoutUser();
                    Intent intent = new Intent(Homepage.this, LoginMain.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        TextView rnn_displayer = navigationView.getHeaderView(0).findViewById(R.id.role_and_name_displayer);
        TextView email_displayer = navigationView.getHeaderView(0).findViewById(R.id.email_displayer);

        rnn_displayer.setText(user.getRole() + " " + user.getFullName());
        email_displayer.setText(user.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_empty, fragment);
        transaction.commit();
    }
}