package hometest;

import homework.Cclass;
import homework.Person;
import homework.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


public class TestSTU {

    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;
    EntityTransaction transaction = null;

    @Before
    public void start(){

//        1.创建 EntityManagerFactory
        String persistenceUnitName = "NewPersistenceUnit";//与persistence.xml的persistence-unit一致
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);

//        2.创建 EntityManager
        entityManager = entityManagerFactory.createEntityManager();

//        3.开启事务
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void end(){
//        5.提交事务
        transaction.commit();

//        6. 关闭 EntityManager
        entityManager.close();

//        7. 关闭 EntityManagerFactory
        entityManagerFactory.close();

    }

    @Test
    public void testIN(){

        Cclass cclass = new Cclass(1,"二五班",new java.sql.Timestamp(System.currentTimeMillis()),20L);
        entityManager.persist(cclass);
        Student student = new Student(1,"张三","男",null,"在读",cclass);
        entityManager.persist(student);
    }

    @Test
    public void testINC(){

        Cclass cclass = new Cclass(2,"二六班",new java.sql.Timestamp(System.currentTimeMillis()),20L);
        entityManager.persist(cclass);
        Student student = new Student(2,"李四","男",null,"在读",cclass);
        Student student2 = new Student(3,"王八","男",null,"在读",cclass);
        entityManager.persist(student);
        entityManager.persist(student2);
    }

    @Test
    public void testUP(){

        Cclass cclass = entityManager.find(Cclass.class, 1L);
        Student student = entityManager.find(Student.class, 3L);
        student.setCclass(cclass);

    }

    @Test
    public void testQU(){
        Query query = entityManager.createQuery("from Student s where s.sid =2");
        List<Student> list = query.getResultList();
        for (Student p : list) {
            System.out.println(p);
        }

    }

    @Test
    public void testQUC(){
        Query query = entityManager.createQuery("from Student s where s.sname ='王八'");
        Student o = (Student) query.getSingleResult();
        long sid = o.getCclass().getCid();
        System.out.println("sid = " + sid);
        Cclass cclass = entityManager.find(Cclass.class, sid);
        System.out.println(cclass);

    }

    @Test
    public void testDE(){

        Student student = entityManager.find(Student.class, 2);
        student.setStatus("开除");
        entityManager.merge(student);
    }
    @Test
    public void testDel(){

        Student student = entityManager.find(Student.class, 2);
        entityManager.remove(student);

    }
    @Test
    public void testDelC(){

        Cclass cclass = entityManager.find(Cclass.class, 1);
        Cclass cclass2 = entityManager.find(Cclass.class, 2);
        entityManager.remove(cclass);
        entityManager.remove(cclass2);

    }

}
