package hometest;

import homework.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王叔叔
 * @create 2020/10/17 16:24
 */
public class TestPR {


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

        Person person = new Person(1,"AAA","123",new java.sql.Timestamp(System.currentTimeMillis()),0L);
        Person person2 = new Person(2,"BBB","123",new java.sql.Timestamp(System.currentTimeMillis()),0L);
        Person person3 = new Person(3,"CCC","123",new java.sql.Timestamp(System.currentTimeMillis()),0L);

        ArrayList<Person> list = new ArrayList<Person>();
        list.add(person);
        list.add(person2);
        list.add(person3);
        for (Person p : list) {
            entityManager.persist(p);
        }
    }

    @Test
    public void testUP(){

        Query query = entityManager.createQuery("from Person where integral = 0");
        List<Person> list = query.getResultList();
        int index = 0;
        for (Person p : list) {
            if (index ==0){
                p.setIntegral(10L);
                entityManager.merge(p);
                break;
            }
            index ++;
        }

    }

    @Test
    public void testDel(){

        Person person = entityManager.find(Person.class, 3L);
        entityManager.remove(person);
    }

    @Test
    public void testSel(){

        String a = "A";
        Query query = entityManager.createQuery("from Person where pname like '%"+a+"%'");
        List<Person> list = query.getResultList();
        for (Person p : list) {
            System.out.println(p);
        }
    }

    @Test
    public void test(){

        Query query = entityManager.createQuery("select sum(integral) from Person");
        Object o1 = query.getSingleResult();
        System.out.println(o1);

    }



}
