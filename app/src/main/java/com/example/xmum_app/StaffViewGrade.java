package com.example.xmum_app;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StaffViewGrade extends Fragment implements CoursesListener{
    private CoursesListener listener;
    private Button insertGradeBtn, refreshPageBtn;
    private static final String KEY_EMPTY = "";
    private EditText etCourseId, etStudentId, etGPA;
    private String CourseID, StudentID;
    private double GPA;
    private TextView ehView;

    @Override
    public void CoursesInputSent(Courses courseSender) {

    }

    @Override
    public void CoursesDataRetrieved() {

    }

    @Override
    public void CoursesEnrollStudent(String CourseID) {

    }

    @Override
    public void CoursesDisenrollStudent(String CourseID) {

    }

    @Override
    public void GradeInputSent(String CourseID, String StudentID, double GPA) {

    }

    @Override
    public void GradeDataRetrieved() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_staff_view_grade, container, false);

        insertGradeBtn = v.findViewById(R.id.insert_grade);
        refreshPageBtn = v.findViewById(R.id.refresh);
        etCourseId = v.findViewById(R.id.course_id_edit_text);
        etStudentId = v.findViewById(R.id.student_id_edit_text);
        etGPA = v.findViewById(R.id.gpa_edit_text);
        ehView = v.getRootView().findViewById(R.id.eh_tv);

        ehView.setText("");
        listener.GradeDataRetrieved();

        insertGradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseID = etCourseId.getText().toString().toLowerCase().trim();
                StudentID = etStudentId.getText().toString().trim();
                GPA = Integer.parseInt(etGPA.getText().toString());

                if (validateInputs()) {
                    ehView.setText("");
                    listener.GradeInputSent(CourseID, StudentID, GPA);
                    listener.GradeDataRetrieved();
                }
            }
        });

        refreshPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ehView.setText("");
                listener.GradeDataRetrieved();
            }
        });

        return v;
    }

    public void setEHViewText(String text){
        ehView.append(text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CoursesListener) {
            listener = (CoursesListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement CoursesListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(CourseID)) {
            etCourseId.setError("Course ID cannot be empty");
            etCourseId.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(StudentID)) {
            etStudentId.setError("Student ID cannot be empty");
            etStudentId.requestFocus();
            return false;
        }
        if (GPA < 0 || GPA > 4) {
            etGPA.setError("GPA cannot smaller than 0 or bigger than 4");
            etGPA.requestFocus();
            return false;
        }

        return true;
    }
}