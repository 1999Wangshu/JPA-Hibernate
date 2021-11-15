package hometest;

import com.pojo.Dept;
import com.pojo.Emp;
import homework.Salgrade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author 王叔叔
 * @create 2020/10/17 15:35
 */
public class TestDE {


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
        Dept dept = new Dept(12,"人事部","");
        entityManager.persist(dept);
        Emp emp = new Emp(7003,"AAA"," SALESMAN",7902L,null,null,null,dept);
        entityManager.persist(emp);

    }

    @Test
    public void testINEM(){
        Dept dept1 = new Dept(13, "研发部", "");
        entityManager.persist(dept1);
        Emp emp2 = new Emp(7004,"BBB"," SALESMAN",7902L,null,null,null,dept1);
        Emp emp3 = new Emp(7005,"CCC"," SALESMAN",7902L,null,null,null,dept1);
        entityManager.persist(emp2);
        entityManager.persist(emp3);

    }

    @Test
    public void testUPIN(){

        Dept dept1 = new Dept(14, "研发二部", "");
        entityManager.persist(dept1);
        Emp emp = entityManager.find(Emp.class, 7005L);
        System.out.println(emp);
        emp.setDept(dept1);
        entityManager.merge(emp);


    }

    @Test
    public void testQU(){

        String v = "人事部";
        String jpql = "from Emp e,Dept d where d.id=e.id and d.dname =?1";
        Query qu = entityManager.createQuery("from Emp e where dept.dname='"+ v+"'");

//      List<Emp> list = qu.setParameter(1,"人事部").getResultList();
        List<Emp> list = qu.getResultList();
        for (Emp emp : list) {
            System.out.println(emp);
        }

    }

    @Test
    public void testDEL(){

        Dept dept = entityManager.find(Dept.class, 14L);
        entityManager.remove(dept);

    }

}
