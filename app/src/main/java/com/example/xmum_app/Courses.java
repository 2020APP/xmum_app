package com.example.xmum_app;

public class Courses {
    public String CourseID;
    public String CourseName;
    public int Credit;
    public String LecturerID;
    public String LecturerName;
    public int StudentNo;

    public Courses() {
    }

    public Courses(String courseID, String courseName, int credit, String lecturerName, int studentNo) {
        CourseID = courseID;
        CourseName = courseName;
        Credit = credit;
        LecturerName = lecturerName;
        StudentNo = studentNo;
    }

    public Courses(String courseID, String courseName, int credit, String lecturerName, String lecturerID, int studentNo) {
        CourseID = courseID;
        CourseName = courseName;
        Credit = credit;
        LecturerName = lecturerName;
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

    public String getLecturerName() {
        return LecturerName;
    }

    public void setLecturerName(String lecturerName) {
        LecturerName = lecturerName;
    }

    public int getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(int studentNo) {
        StudentNo = studentNo;
    }
}
