import com.pojo.Dept;
import com.pojo.Emp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

/**
 * @author 王叔叔
 * @create 2020/10/12 18:49
 */
public class TestComplexMethod {

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
    public void testIn(){

        Dept dept = new Dept() ;
        dept. setDeptno(51L) ;
        dept. setDname ("SSSS") ;
        entityManager. persist (dept) ;
        Emp emp = new Emp() ;
        emp.setEmpno (7002) ;
        emp.setEname ("刘德华");
        emp.setJob (" SALESMAN") ;
        //emp是主控方，要用emp来建立和Dept的关系
        emp .setDept (dept) ;
        entityManager.persist (emp) ;

    }

    @Test
    public void TestQu(){

        Dept dept1 = entityManager.getReference(Dept.class, 11L);
        System.out.println(dept1);
        System.out.println(dept1.getDeptno());
    }
    @Test
    public void TestQuery(){

        //注解fetch = FetchType.EAGER一条sql语句,
        Dept dept = new Dept();
        dept.setDeptno((long)11);
        Dept dept2 = entityManager.find(Dept.class, 51L);
        System.out.println(dept2);

    }

    @Test
    public void testQueryList(){

        Query query = entityManager.createQuery("select deptno,dname from Dept");
        List resultList = query.getResultList();
        for (Object o : resultList) {
            System.out.println(o);
        }
    }

    @Test
    public void testUP(){

        Emp emp = entityManager.find(Emp.class, 7002L);
        Dept dept = entityManager.find(Dept.class, 51L);
        emp.setDept(dept);
        //修改时，事务提交后，会自动调用merge()方法完成修改

    }

    @Test
    public void testDEl(){

        //普通删除,如果部门下有员工不能删除(外键冲突)
        Emp emp = entityManager.find(Emp.class, 51L);
        entityManager.remove(emp);

        //联级删除，需在关联属性的注解中配置，PERSIST//级联添加,REMOVE//级联删除
        //先找到部门,在删除部门后删除部门下的所有员工
        Dept dept = entityManager.find(Dept.class, 51L);
        entityManager.remove(dept);

    }


}
