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

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;


public class Homepage extends AppCompatActivity implements StaffViewCourses.StaffViewCoursesListener{
    private StaffViewCourses staffViewCourses;
    private SessionHandler session;
    private DrawerLayout drawerLayout;
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_COURSE_ID = "course_id";
    private static final String KEY_COURSE_NAME = "course_name";
    private static final String KEY_CREDIT = "credit";
    private static final String KEY_LECTURER_ID = "lecturer_id";
    private static final String KEY_STUDENT_NO = "student_no";
    private String create_courses_url = "http://10.0.2.2:80/xmum_app_server/create_courses.php";
    final LoadingDialog loadingDialog = new LoadingDialog(Homepage.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        session = new SessionHandler(getApplicationContext());

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
                if(itemId == R.id.nav_courses && session.getUserDetails().getRole().equals("Staff")) {
                    staffViewCourses = new StaffViewCourses();
                    replaceFragment(staffViewCourses);
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

        rnn_displayer.setText(session.getUserDetails().getRole() + " " + session.getUserDetails().getFullName());
        email_displayer.setText(session.getUserDetails().getEmail());
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

    @Override
    public void onSVCInputSent(String CourseID, String CourseName, int Credit, int StudentNo) {
        loadingDialog.startLoadingDialog();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_COURSE_ID, CourseID);
            request.put(KEY_COURSE_NAME, CourseName);
            request.put(KEY_CREDIT, Credit);
            request.put(KEY_LECTURER_ID, session.getUserDetails().getId());
            request.put(KEY_STUDENT_NO, StudentNo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, create_courses_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismissLoadingDialog();
                        try {
                            Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissLoadingDialog();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
}