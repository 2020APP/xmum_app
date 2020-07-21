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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Homepage extends AppCompatActivity implements CoursesListener{
    private StaffViewCourses staffViewCourses;
    private StudentViewCourses studentViewCourses;
    private StaffViewGrade staffViewGrade;
    private StudentViewGrade studentViewGrade;
    private SessionHandler session;
    private DrawerLayout drawerLayout;
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_COURSE_ID = "course_id";
    private static final String KEY_COURSE_NAME = "course_name";
    private static final String KEY_CREDIT = "credit";
    private static final String KEY_GPA = "gpa";
    private static final String KEY_LECTURER_ID = "lecturer_id";
    private static final String KEY_LECTURER_NAME = "full_name";
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_STUDENT_NO = "student_no";
    private String create_courses_url = "http://10.0.2.2:80/xmum_app_server/create_courses.php";
    private String retrieve_courses_url = "http://10.0.2.2:80/xmum_app_server/retrieve_courses.php";
    private String enroll_student_url = "http://10.0.2.2:80/xmum_app_server/enroll_student.php";
    private String disenroll_student_url = "http://10.0.2.2:80/xmum_app_server/disenroll_student.php";
    private String update_grade_url = "http://10.0.2.2:80/xmum_app_server/update_grade.php";
    private String retrieve_grade_url = "http://10.0.2.2:80/xmum_app_server/retrieve_grade.php";

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
                    replaceFragment(staffViewCourses, "staffViewCourses");
                }
                if(itemId == R.id.nav_courses && session.getUserDetails().getRole().equals("Student")) {
                    studentViewCourses = new StudentViewCourses();
                    replaceFragment(studentViewCourses, "studentViewCourses");
                }
                if(itemId == R.id.nav_grade && session.getUserDetails().getRole().equals("Staff")) {
                    staffViewGrade = new StaffViewGrade();
                    replaceFragment(staffViewGrade, "staffViewGrade");
                }
                if(itemId == R.id.nav_grade && session.getUserDetails().getRole().equals("Student")) {
                    studentViewGrade = new StudentViewGrade();
                    replaceFragment(studentViewGrade, "studentViewGrade");
                }
                if(itemId == R.id.nav_weather) {
                    Intent intent = new Intent(Homepage.this, WeatherMainActivity.class);
                    startActivity(intent);
                }
                if(itemId == R.id.nav_logout) {
                    session.logoutUser();
                    finish();
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

    public void replaceFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_empty, fragment, fragmentTag);
        transaction.commit();
    }

    @Override
    public void CoursesInputSent(Courses course) {
        JSONObject request = new JSONObject();
        course.setLecturerID(session.getUserDetails().getId());
        try {
            //Populate the request parameters
            request.put(KEY_COURSE_ID, course.getCourseID());
            request.put(KEY_COURSE_NAME, course.getCourseName());
            request.put(KEY_CREDIT, course.getCredit());
            request.put(KEY_LECTURER_ID, course.getLecturerID());
            request.put(KEY_STUDENT_NO, course.getStudentNo());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjectRequest = new JsonObjectRequest
                (Request.Method.POST, create_courses_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjectRequest);
    }

    @Override
    public void CoursesDataRetrieved() {
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.POST, retrieve_courses_url, (String) null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int n = 0; n < response.length(); n++) {
                                JSONObject CoursesData = response.getJSONObject(n);

                                staffViewCourses = (StaffViewCourses) getSupportFragmentManager().findFragmentByTag("staffViewCourses");
                                studentViewCourses = (StudentViewCourses) getSupportFragmentManager().findFragmentByTag("studentViewCourses");

                                if (staffViewCourses != null && staffViewCourses.isAdded()) {
                                    staffViewCourses.setCourseViewText(
                                            "Course no.: " + Integer.sum(n, 1) + "\n"
                                                    + "Course ID: " + CoursesData.getString(KEY_COURSE_ID) + "\n"
                                                    + "Course Name: " + CoursesData.getString(KEY_COURSE_NAME) + "\n"
                                                    + "Credit: " + CoursesData.getString(KEY_CREDIT) + "\n"
                                                    + "Lecturer: " + CoursesData.getString(KEY_LECTURER_NAME) + "\n"
                                                    + "Student no.: " + CoursesData.getString(KEY_STUDENT_NO) + "\n\n"
                                    );
                                }

                                if (studentViewCourses != null && studentViewCourses.isAdded()) {
                                    studentViewCourses.setCourseViewText(
                                            "Course no.: " + Integer.sum(n, 1) + "\n"
                                                    + "Course ID: " + CoursesData.getString(KEY_COURSE_ID) + "\n"
                                                    + "Course Name: " + CoursesData.getString(KEY_COURSE_NAME) + "\n"
                                                    + "Credit: " + CoursesData.getString(KEY_CREDIT) + "\n"
                                                    + "Lecturer: " + CoursesData.getString(KEY_LECTURER_NAME) + "\n"
                                                    + "Student no.: " + CoursesData.getString(KEY_STUDENT_NO) + "\n\n"
                                    );
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    @Override
    public void CoursesEnrollStudent(String CourseID) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_COURSE_ID, CourseID);
            request.put(KEY_STUDENT_ID, session.getUserDetails().getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjectRequest = new JsonObjectRequest
                (Request.Method.POST, enroll_student_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjectRequest);
    }

    @Override
    public void CoursesDisenrollStudent(String CourseID) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_COURSE_ID, CourseID);
            request.put(KEY_STUDENT_ID, session.getUserDetails().getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjectRequest = new JsonObjectRequest
                (Request.Method.POST, disenroll_student_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjectRequest);
    }

    @Override
    public void GradeInputSent(String CourseID, String StudentID, double GPA) {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_COURSE_ID, CourseID);
            request.put(KEY_STUDENT_ID, StudentID);
            request.put(KEY_GPA, GPA);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjectRequest = new JsonObjectRequest
                (Request.Method.POST, update_grade_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjectRequest);
    }

    @Override
    public void GradeDataRetrieved() {
        JSONObject request = new JSONObject();
        if (session.getUserDetails().getRole().equals("Student")) {
            try {
                //Populate the request parameters
                request.put(KEY_STUDENT_ID, session.getUserDetails().getId());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            request = (JSONObject) null;
        }
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.POST, retrieve_grade_url, request, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int n = 0; n < response.length(); n++) {
                                JSONObject GradeData = response.getJSONObject(n);

                                staffViewGrade = (StaffViewGrade) getSupportFragmentManager().findFragmentByTag("staffViewGrade");
                                studentViewGrade = (StudentViewGrade) getSupportFragmentManager().findFragmentByTag("studentViewGrade");

                                if (staffViewGrade != null && staffViewGrade.isAdded()) {
                                    staffViewGrade.setEHViewText(
                                            "Enrollment no.: " + Integer.sum(n, 1) + "\n"
                                                    + "Course ID: " + GradeData.getString(KEY_COURSE_ID) + "\n"
                                                    + "Student ID: " + GradeData.getString(KEY_STUDENT_ID) + "\n"
                                                    + "GPA: " + GradeData.getString(KEY_GPA) + "\n\n"
                                    );
                                }

                                if (studentViewGrade != null && studentViewGrade.isAdded()) {
                                    studentViewGrade.setEHViewText(
                                            "Subject no.: " + Integer.sum(n, 1) + "\n"
                                                    + "Course ID: " + GradeData.getString(KEY_COURSE_ID) + "\n"
                                                    + "Course Name: " + GradeData.getString(KEY_COURSE_NAME) + "\n"
                                                    + "GPA: " + GradeData.getString(KEY_GPA) + "\n\n"
                                    );
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
}