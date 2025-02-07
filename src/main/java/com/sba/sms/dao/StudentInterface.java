package com.sba.sms.dao;

import com.sba.sms.model.Course;
import com.sba.sms.model.Student;

import java.util.List;

public interface StudentInterface {
    List<Student> getAllStudents();
    void createStudent(Student student);

    Student getStudentByEmail(String email);

    boolean validateStudent(String email, String password);

    void registerStudentToCourse(String email, int courseId);

    List<Course> getStudentCourses(String email);
}
