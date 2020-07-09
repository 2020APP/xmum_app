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
import android.widget.TextView;

public class StudentViewGrade extends Fragment implements CoursesListener{
    private CoursesListener listener;
    private Button refreshPageBtn;
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
        View v = inflater.inflate(R.layout.fragment_student_view_grade, container, false);

        refreshPageBtn = v.findViewById(R.id.refresh);
        ehView = v.getRootView().findViewById(R.id.eh_tv);

        ehView.setText("");
        listener.GradeDataRetrieved();

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
}