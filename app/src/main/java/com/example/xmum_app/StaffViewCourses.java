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

public class StaffViewCourses extends Fragment implements CoursesListener{
    private CoursesListener listener;
    private Button createCourseBtn, refreshPageBtn;
    private static final String KEY_EMPTY = "";
    private EditText etCourseId, etCourseName, etCredit;
    private Courses courseSender;
    private TextView CourseView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_staff_view_courses, container, false);

        createCourseBtn = v.findViewById(R.id.create_course);
        refreshPageBtn = v.findViewById(R.id.refresh);
        etCourseId = v.findViewById(R.id.course_id_edit_text);
        etCourseName = v.findViewById(R.id.course_name_edit_text);
        etCredit = v.findViewById(R.id.credit_edit_text);
        CourseView = v.getRootView().findViewById(R.id.course_tv);

        CourseView.setText("");
        listener.CoursesDataRetrieved();

        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseSender = new Courses();
                courseSender.setCourseID(etCourseId.getText().toString().toLowerCase().trim());
                courseSender.setCourseName(etCourseName.getText().toString().trim());
                courseSender.setCredit(Integer.parseInt(etCredit.getText().toString()));
                courseSender.setStudentNo(0);

                if (validateInputs()) {
                    CourseView.setText("");
                    listener.CoursesInputSent(courseSender);
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
        if (KEY_EMPTY.equals(courseSender.getCourseID())) {
            etCourseId.setError("Course ID cannot be empty");
            etCourseId.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(courseSender.getCourseName())) {
            etCourseName.setError("Course name cannot be empty");
            etCourseName.requestFocus();
            return false;
        }
        if (courseSender.getCredit() <= 0 || courseSender.getCredit() >= 5) {
            etCredit.setError("Credit cannot smaller than 1 or bigger than 4");
            etCredit.requestFocus();
            return false;
        }

        return true;
    }
}