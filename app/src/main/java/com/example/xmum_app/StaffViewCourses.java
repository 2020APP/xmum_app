package com.example.xmum_app;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class StaffViewCourses extends Fragment {
    Button createCourseBtn, refreshPageBtn;
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_COURSE_ID = "course_id";
    private static final String KEY_COURSE_NAME = "course_name";
    private static final String KEY_CREDIT = "credit";
    private static final String KEY_LECTURER_ID = "lecturer_id";
    private static final String KEY_STUDENT_NO = "student_no";
    private static final String KEY_EMPTY = "";
    final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
    private EditText etCourseId;
    private EditText etCourseName;
    private EditText etCredit;
    private String CourseID;
    private String CourseName;
    private int Credit;
    private String LecturerID;
    private int StudentNo;
    private String create_courses_url = "http://10.0.2.2:80/xmum_app_server/create_courses.php";
    private String check_courses_url = "http://10.0.2.2:80/xmum_app_server/check_courses.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_staff_view_courses, container, false);

        createCourseBtn = v.findViewById(R.id.create_course);
        refreshPageBtn = v.findViewById(R.id.refresh);

        etCourseId = v.findViewById(R.id.course_id_edit_text);
        etCourseName = v.findViewById(R.id.course_name_edit_text);
        etCredit = v.findViewById(R.id.credt_edit_text);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert this.getArguments() != null;
        final String userID = this.getArguments().getString("UserID");

        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseID = etCourseId.getText().toString().toLowerCase().trim();
                CourseName = etCourseName.getText().toString().trim();
                Credit = Integer.parseInt(etCredit.getText().toString());
                LecturerID = userID;
                StudentNo = 0;

                if (validateInputs()) {
                    createCourse();
                }
            }
        });
    }
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(CourseID)) {
            etCourseId.setError("Course ID cannot be empty");
            etCourseId.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(CourseName)) {
            etCourseName.setError("Course name cannot be empty");
            etCourseName.requestFocus();
            return false;
        }
        if (Credit <= 0 || Credit >= 5) {
            etCredit.setError("Credit cannot smaller than 1 or bigger than 4");
            etCredit.requestFocus();
            return false;
        }

        return true;
    }

    private void createCourse() {
        loadingDialog.startLoadingDialog();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_COURSE_ID, CourseID);
            request.put(KEY_COURSE_NAME, CourseName);
            request.put(KEY_CREDIT, Credit);
            request.put(KEY_LECTURER_ID, LecturerID);
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
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                Toast.makeText(getActivity().getApplicationContext(),
                                        response.getString("New Course Created!"), Toast.LENGTH_SHORT).show();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if CourseID is already existing
                                etCourseId.setError("This ID already had an account.");
                                etCourseId.requestFocus();

                            }else{
                                Toast.makeText(getActivity().getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissLoadingDialog();

                        //Display error message whenever an error occurs
                        Toast.makeText(getActivity().getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsArrayRequest);
    }
}