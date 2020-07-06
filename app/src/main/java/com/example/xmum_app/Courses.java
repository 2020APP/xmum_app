package com.example.xmum_app;

public class Courses {
    public int CourseNo;
    public String CourseID;
    public String CourseName;
    public int Credit;
    public String Lecturer;
    public int StudentNo;

    public int getCourseNo() {
        return CourseNo;
    }

    public int getCredit() {
        return Credit;
    }

    public int getStudentNo() {
        return StudentNo;
    }

    public String getCourseID() {
        return CourseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getLecturer() {
        return Lecturer;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public void setCourseNo(int courseNo) {
        CourseNo = courseNo;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public void setLecturer(String lecturer) {
        Lecturer = lecturer;
    }

    public void setStudentNo(int studentNo) {
        StudentNo = studentNo;
    }
}
