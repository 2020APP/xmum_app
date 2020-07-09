package com.example.xmum_app;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StudentViewCourses extends Fragment implements CoursesListener{
    private CoursesListener listener;
    private Button enrollCourseBtn, unenrollCourseBtn, refreshPageBtn;
    private static final String KEY_EMPTY = "";
    private EditText etCourseId;
    private TextView CourseView;
    private String CourseID;

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
        View v = inflater.inflate(R.layout.fragment_student_view_courses, container, false);

        enrollCourseBtn = v.findViewById(R.id.enroll_course);
        unenrollCourseBtn = v.findViewById(R.id.disenroll_course);
        refreshPageBtn = v.findViewById(R.id.refresh);
        etCourseId = v.findViewById(R.id.course_id_edit_text);
        CourseView = v.getRootView().findViewById(R.id.course_tv);

        CourseView.setText("");
        listener.CoursesDataRetrieved();

        enrollCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseID = etCourseId.getText().toString().toLowerCase().trim();

                if (validateInputs()) {
                    CourseView.setText("");
                    listener.CoursesEnrollStudent(CourseID);
                    listener.CoursesDataRetrieved();
                }
            }
        });

        unenrollCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseID = etCourseId.getText().toString().toLowerCase().trim();

                if (validateInputs()) {
                    CourseView.setText("");
                    listener.CoursesDisenrollStudent(CourseID);
                    listener.CoursesDataRetrieved();
                }
            }
        });

        refreshPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseView.setText("");
                listener.CoursesDataRetrieved();
            }
        });

        return v;
    }

    public void setCourseViewText(String text){
        CourseView.append(text);
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
        return true;
    }
}