import com.pojo.Dept;
import com.pojo.Emp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author 王叔叔
 * @create 2020/10/27 16:11
 */
public class TestManyOne {


    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;
    EntityTransaction transaction = null;

    @Before
    public void start(){

        // 1.创建 EntityManagerFactory
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

    //单向多对1, 先保存1的一端, 后保存n的一端, 这样不会发送额外的UPDATE 语句.
    @Test
    public void testInMany(){
        Dept dept = new Dept() ;
        dept. setDeptno(1L) ;
        dept. setDname ("SSSS") ;
        entityManager. persist (dept) ;
        Emp emp = new Emp() ;
        emp.setEmpno (102L) ;
        emp.setEname ("zs");
        emp.setJob (" SALESMAN") ;
        Emp emp2 = new Emp() ;
        emp2.setEmpno (103L) ;
        emp2.setEname ("ls");
        emp2.setJob (" SALESMAN") ;

        //设置关联关系
        emp.setDept (dept);
        emp2.setDept(dept);

        entityManager.persist(dept);
        entityManager.persist(emp);
        entityManager.persist(emp2);

    }
}
