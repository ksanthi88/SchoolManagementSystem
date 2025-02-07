import com.sba.sms.model.Course;
import com.sba.sms.model.Student;
import com.sba.sms.service.CourseService;
import com.sba.sms.service.StudentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentServiceTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private StudentService studentService;
    private CourseService courseService;


    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        studentService = new StudentService();
        courseService = new CourseService();
    }

    @AfterEach
    void tearDown() {

        if (transaction != null && transaction.getStatus().canRollback()) {
            transaction.rollback(); // Rollback only if possible
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setName("test");
        student.setEmail("test@test.com");
        student.setPassword("123456");
        studentService.createStudent(student);
        session.flush();
        session.clear();
        Student fetchedStudent=studentService.getStudentByEmail(student.getEmail());
        assertEquals("test@test.com", fetchedStudent.getEmail());
    }
}

