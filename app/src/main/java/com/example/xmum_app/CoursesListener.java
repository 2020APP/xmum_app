package com.example.xmum_app;

public interface CoursesListener {
    void CoursesInputSent(Courses courseSender);
    void CoursesDataRetrieved();
    void CoursesEnrollStudent(String CourseID);
    void CoursesDisenrollStudent(String CourseID);
}
