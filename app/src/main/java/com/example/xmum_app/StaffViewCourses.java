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

public class StaffViewCourses extends Fragment {
    private StaffViewCoursesListener listener;
    Button createCourseBtn, refreshPageBtn;
    private static final String KEY_EMPTY = "";
    private EditText etCourseId;
    private EditText etCourseName;
    private EditText etCredit;
    private Courses course;

    public interface StaffViewCoursesListener {
        void onSVCInputSent(Courses course);
        void onSVCDataRetrieved();
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

        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course = new Courses();
                course.setCourseID(etCourseId.getText().toString().toLowerCase().trim());
                course.setCourseName(etCourseName.getText().toString().trim());
                course.setCredit(Integer.parseInt(etCredit.getText().toString()));
                course.setStudentNo(0);

                if (validateSVCInputs()) {
                    listener.onSVCInputSent(course);
                }
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof StaffViewCoursesListener) {
            listener = (StaffViewCoursesListener) context;
        }else {
            throw new RuntimeException(context.toString()
            + " must implement StaffViewCourses Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private boolean validateSVCInputs() {
        if (KEY_EMPTY.equals(course.getCourseID())) {
            etCourseId.setError("Course ID cannot be empty");
            etCourseId.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(course.getCourseName())) {
            etCourseName.setError("Course name cannot be empty");
            etCourseName.requestFocus();
            return false;
        }
        if (course.getCredit() <= 0 || course.getCredit() >= 5) {
            etCredit.setError("Credit cannot smaller than 1 or bigger than 4");
            etCredit.requestFocus();
            return false;
        }

        return true;
    }
}