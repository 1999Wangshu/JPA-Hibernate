import com.pojo.Emp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 王叔叔
 * @create 2020/10/21 19:25
 */
public class TestDEPT {

    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;
    EntityTransaction transaction = null;

    @Before
    public void start(){

//        1.Persistence创建 EntityManagerFactory的实例
        String persistenceUnitName = "NewPersistenceUnit";//与persistence.xml的persistence-unit一致
        //Persistence的重载方法,properties配置参数,设置false不打印sql了
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.show_sql",false);

        entityManagerFactory =
                //Persistence.createEntityManagerFactory(persistenceUnitName);
                Persistence.createEntityManagerFactory(persistenceUnitName,properties);


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

    //链表查询
    @Test
    public void testQUINNER(){

        String jpql = "select e from Emp e inner join e.dept d";

        List<Emp> list = entityManager.createQuery(jpql,Emp.class).getResultList();

        for (Emp emp : list) {
            System.out.println(emp);
        }
    }

    //把d对象赋给e
    @Test
    public void testQUFetch(){

        String jpql = "from Emp e inner join fetch e.dept d";

        List<Emp> list = entityManager.createQuery(jpql).getResultList();

        for (Emp emp : list) {
            System.out.println(emp);
        }
    }

    //添加
    @Test
    public void testIn(){

        Emp emp = new Emp();
        emp.setEmpno(101L);
        emp.setJob("SALESMAN");
        emp.setHiredate(new Date());
    }
}
