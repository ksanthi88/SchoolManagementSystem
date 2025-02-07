package com.sba.sms.service;

import com.sba.sms.dao.StudentInterface;
import com.sba.sms.model.Course;
import com.sba.sms.model.Student;
import com.sba.sms.util.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.id.uuid.StandardRandomStrategy;

import java.nio.channels.ScatteringByteChannel;
import java.util.List;

public class StudentService implements StudentInterface  {

    @Override
    public List<Student> getAllStudents() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            List<Student> students = session.createQuery("from Student",Student.class).list();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return List.of();
    }

    @Override
    public void createStudent(Student student) {
Transaction tx = null;
try(Session session = HibernateUtil.getSessionFactory().openSession()) {
    tx = session.beginTransaction();
    session.persist(student);
    tx.commit();
} catch (Exception e) {
    if (tx != null) {
        tx.rollback();
        e.printStackTrace();
    }
}
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student student = session.get(Student.class, email);
            return student;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean validateStudent(String email, String password) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student student = session.get(Student.class, email);
            return student != null && student.getPassword().equals(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);

            if (student == null) {
                System.out.println("No student found with email: " + email);
                return;
            }
            if (course == null) {
                System.out.println("No course found with ID: " + courseId);
                return;
            }

            if (!student.getCourses().contains(course)) {
                student.getCourses().add(course);
                session.merge(student);  // Update the student with the new course
                System.out.println("Student " + email + " successfully registered for course: " + course.getCourseName());
            } else {
                System.out.println("Student " + email + " is already registered for the course: " + course.getCourseName());
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student student = session.get(Student.class, email);
            return student != null ? List.copyOf(student.getCourses()) : null;
        }
    }
}



