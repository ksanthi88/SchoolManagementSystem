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
@ToString(exclude="courses")
@Entity
@Table(name="student")
public class Student {
    @Id
    @Column(name="email",length=50,nullable=false,unique=true)
    private String email;
    @Column(name="name",length=50,nullable=false)
    private String name;
    @Column(name="password",length=50,nullable=false)
    private String password;
   @ManyToMany(fetch= FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name="student_courses",
            joinColumns=@JoinColumn(name="studnet_email"),
            inverseJoinColumns = @JoinColumn(name="cousres_id")
    )
    private Set<Course>  courses=new HashSet<>();
    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
