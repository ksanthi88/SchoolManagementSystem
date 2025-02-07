package com.sba.sms.dao;

import com.sba.sms.model.Course;

import java.util.List;

public interface CourseInterface {
    void createCourse(Course course);
    Course getCourseById(int courseId);
    List<Course> getAllCourses();
}
