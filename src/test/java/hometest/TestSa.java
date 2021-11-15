package hometest;

import homework.Salgrade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

/**
 * @author 王叔叔
 * @create 2020/10/17 13:43
 */
public class TestSa {

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
    public void testIN6(){
        Salgrade salgrade = new Salgrade(6L,6L,60L,100L);
        entityManager.persist(salgrade);

    }
    @Test
    public void testUP5(){
        Salgrade salgrade = entityManager.find(Salgrade.class, 5L);
        System.out.println(salgrade);
        salgrade.setLosal(50L);
        salgrade.setHisal(500L);
        //entityManager.clear();
        //entityManager.merge(salgrade);

    }

    @Test
    public void testDEL(){

        Salgrade salgrade = entityManager.find(Salgrade.class, 6L);
        entityManager.remove(salgrade);
    }

    @Test
    public void testQU(){

        Salgrade salgrade = entityManager.find(Salgrade.class, 3L);
        System.out.println(salgrade);
    }

    @Test
    public void testQUAll(){
        Query query = entityManager.createQuery("from Salgrade");
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }

    }
}
