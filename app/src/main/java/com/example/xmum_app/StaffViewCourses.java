package com.example.xmum_app;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

public class StaffViewCourses extends Fragment {
    private TableLayout mTableLayout;
    final LoadingDialog loadingDialog = new LoadingDialog(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff_view_courses, container, false);

        mTableLayout = view.findViewById(R.id.tablecourses);
        mTableLayout.setStretchAllColumns(true);

        return view;
    }

}