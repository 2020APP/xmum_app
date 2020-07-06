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
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;


public class Homepage extends AppCompatActivity {
    private SessionHandler session;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        final String privilege = user.getRole();
        final String userID = user.getId();

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
                if(itemId == R.id.nav_courses && privilege.equals("Staff")) {
                    StaffViewCourses fragment = new StaffViewCourses();
                    Bundle args = new Bundle();
                    args.putString("UserID", userID);
                    fragment.setArguments(args);
                    replaceFragment(fragment);
                }
                else if(itemId == R.id.nav_logout) {
                    replaceFragment(new StudentViewCourses());
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
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