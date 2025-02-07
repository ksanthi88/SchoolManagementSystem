package com.sba.sms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@ToString(exclude="students")
@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50, nullable = false)
    private String courseName;
    @Column(length = 50, nullable = false)
    private String instructorName;
    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER,cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    private Set<Student> students=new HashSet<>();

    public Course(String courseName, String instructorName) {
        this.courseName = courseName;
        this.instructorName = instructorName;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(courseName, course.courseName) && Objects.equals(instructorName, course.instructorName) && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, instructorName, students);
    }
}
