package com.sba.sms.service;

import com.sba.sms.dao.CourseInterface;
import com.sba.sms.model.Course;
import com.sba.sms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CourseService implements CourseInterface {
    @Override
    public void createCourse(Course course) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx=session.beginTransaction();
            session.persist(course);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public Course getCourseById(int courseId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Course.class, courseId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Course",Course.class).list();
        } catch (Exception e){
            e.printStackTrace();
        }
        return List.of();
    }
}
