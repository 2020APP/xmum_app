package com.example.xmum_app;

public class Courses {
    public String CourseID;
    public String CourseName;
    public int Credit;
    public String LecturerID;
    public int StudentNo;

    public Courses() {
    }

    public Courses(String courseID, String courseName, int credit, String lecturerID, int studentNo) {
        CourseID = courseID;
        CourseName = courseName;
        Credit = credit;
        LecturerID = lecturerID;
        StudentNo = studentNo;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public int getCredit() {
        return Credit;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public String getLecturerID() {
        return LecturerID;
    }

    public void setLecturerID(String lecturerID) {
        LecturerID = lecturerID;
    }

    public int getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(int studentNo) {
        StudentNo = studentNo;
    }
}
