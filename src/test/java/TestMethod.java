import com.pojo.Dept;
import com.pojo.Emp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * @author 王叔叔
 * @create 2020/10/27 14:46
 */
public class TestMethod {

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

    //类似session的get()方法
    @Test
    public void TestFind(){

        Dept dept1 = entityManager.find(Dept.class, 11L);
        System.out.println("---------------");
        System.out.println(dept1);
    }

    //session的load(),发现-----横线打印在对前面
    @Test
    public void TestReference(){

        Dept dept1 = entityManager.getReference(Dept.class, 11L);
        System.out.println("---------------");
        System.out.println(dept1);//代理对象,使用到时才发送sql语句
    }

    //类似persist()方法,是对象由临时状态变为持久化状态,但设置主键生成策略id了会直接报异常
    @Test
    public void testIn(){
        //4.进行持久化操作
        Dept dept = new Dept();
        dept.setDeptno(9);
        dept.setDname("baibai");
        dept.setLoc("AAA");
        //添加
        entityManager.persist(dept);
    }

    //类似于session的delete方法. 把对象对应的记录从数据库中移除
    //但该方法只能移除 持久化对象. 而hibernate的还可以移除游离对象.
    @Test
    public void testRemove(){
        //Emp emp = new Emp();
        //emp.setEmpno(12L);//游离对象

        Emp emp = entityManager.find(Emp.class, 12L);
        entityManager.remove(emp);
    }

    //修改临时对象,类似于saveAndUpdate()
    @Test
    public void testMeg(){
        //1.传入临时对象,它会创建一个新对象,把临时对象复制到新对象中,再执行insert,还会返回新对象
        //即新的对象中有 id, 以前的临时对象中没有 id.
        Emp emp = new Emp();
        emp.setEname("baibai");
        emp.setJob("SALESMAN");
        emp.setHiredate(new Date());

        Emp emp1 = entityManager.merge(emp);//返回新对象
        System.out.println(emp.getEmpno());//null
        System.out.println(emp1.getEmpno());
    }

    //若传入的是一个游离对象, 即传入的对象有 OID.
    //2. 若在EntityManager缓存中没有该对象,数据库中也没有对应的记录
    //JPA 会创建一个新的对象,把当前游离对象的属性复制到新创建的对象中,执行insert
    @Test
    public void testMerge2(){
        Emp emp = new Emp();
        emp.setEname("xiaobai");
        emp.setJob("SALESMAN");
        emp.setHiredate(new Date());
        emp.setEmpno(100L);
        Emp emp2 = entityManager.merge(emp);

        System.out.println("customer#id:" + emp.getEmpno());
        System.out.println("customer2#id:" + emp2.getEmpno());
    }

    //游离对象, 即传入的对象有 OID.
    //3. 若在 EntityManager缓存中没有该对象,在数据库中有对应的记录
    //JPA 会查询该记录,返回记录对应的对象,再把游离对象的属性复制到查询到的对象中,执行update
    @Test
    public void testMerge3(){
        Emp emp = new Emp();
        emp.setEname("xiaobai");
        emp.setJob("SALESMAN");
        emp.setHiredate(new Date());
        emp.setEmpno(100L);

        Emp emp2 = entityManager.merge(emp);
        System.out.println(emp==emp2);//false
    }

    //游离对象
    //4.若在 EntityManager 缓存中有对应的对象
    //JPA会把游离对象的属性复制到查询到EntityManager缓存中的对象中,缓存对象执行UPDATE.
    @Test
    public void testMerge4(){
        Emp emp = new Emp();
        emp.setEname("xiaobai");
        emp.setJob("SALESMAN");
        emp.setHiredate(new Date());
        emp.setEmpno(100L);

        Emp emp1 = entityManager.find(Emp.class, 100L);
        //并不是两个相同的对象,而是复制到新对象
        entityManager.merge(emp);
        System.out.println(emp == emp1); //false
    }

    /**
     * 等同hibernate的flush方法.强制发生sql语句
     */
    @Test
    public void testFlush(){
        Emp emp = entityManager.find(Emp.class, 100L);
        System.out.println(emp);
        emp.setEname("baibai");

        entityManager.flush();
    }

    /**
     * 同hibernate的refresh方法.会发送一条新的查询语句
     */
    @Test
    public void testRefresh(){
        Emp emp = entityManager.find(Emp.class, 100L);
        emp = entityManager.find(Emp.class, 100L);

        entityManager.refresh(emp);
    }

    @Test
    public void testActive(){

        boolean b = transaction.isActive();
        System.out.println(b);
    }
}
