import com.pojo.Dept;
import com.pojo.Emp;
import com.pojo.Manager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author 王叔叔
 * @create 2020/10/27 15:35
 */
public class TestManyToOne {


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

    //默认情况下, 使用左外连接的方式来获取n一端对象
    //@ManyToOne(fetch = FetchType.LAZY)启用懒加载
    @Test
    public void testManyToOneFind(){
        Emp emp = entityManager.find(Emp.class, 102L);
        System.out.println(emp.getEname());

        System.out.println(emp.getDept().getDname());
    }

    //不能直接删除 1 的一端, 因为有外键约束.
    @Test
    public void testManyToOneRemove(){
        Dept dept = entityManager.find(Dept.class, 1L);
        entityManager.remove(dept);
    }

    //根据员工修改部门
    @Test
    public void testManyToOneUpdate(){
        Emp emp = entityManager.find(Emp.class, 102L);
        emp.getDept().setDname("ABAB");
    }

    //单向1 对 多的关系,是1维护,而多不维护,所以插入多的一端时并不会插入外键列,
    // 在插入后再修改会多出update语句,跟插入多或1的顺序没有关系
    //默认使用懒加载策略
    public void testAdd(){

    }
    @Test
    public void testOnToManyFind(){
        System.out.println("====="+entityManager);
        Dept dept = entityManager.find(Dept.class, 30L);
        System.out.println(dept.getDname());

        System.out.println(dept.getEmps().size());
    }

    //默认能直接删除 1 的一端, 会把关联的多的一端的外键置空再删除
    //可以修改cascade属性 CascadeType.REMOVE,设置级联删除
    @Test
    public void testOneToManyRemove(){
        Dept dept = entityManager.find(Dept.class, 1L);
        entityManager.remove(dept);
    }
    //可以根据1修改多,
    public void testUpdate(){
        Dept dept = entityManager.find(Dept.class, 1L);
        dept.getEmps().iterator().next().setEname("xxxxx");
    }

    //双向 1对n 的关联关系,执行保存时
    //在没有@OneToMany中mappedBy = "dept"设置主控方,让emps,多的一方维护关联关系(减少sql语句发生)
    //先保存 n 的一端,再保存 1 的一端,会多出 n 条 UPDATE 语句.
    //若先保存 1 的一端, 则会多出 n 条 UPDATE 语句

    //注意:若在 1的一端的中使用 mappedBy属性, 则就不能再使用 @JoinColumn 属性了.
    @Test
    public void testOneInMany(){
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

    //双向1-1的关联关系(基于外键),先保存不维护关联关系的一方(没有外键的),这样不会多出UPDATE 语句.
    @Test
    public void testOneToOnePersistence(){
        Manager mgr = new Manager();
        mgr.setId(1L);
        mgr.setMgrName("M-BB");

        Dept dept = new Dept();
        dept.setDeptno(2L);
        dept.setDname("D-BB");

        //设置关联关系
        mgr.setDept(dept);
        dept.setMgr(mgr);

        //执行保存操作
        entityManager.persist(dept);
        entityManager.persist(mgr);
    }

    //1.默认情况下,获取维护关联关系的一方, 则会通过左外连接获取其关联的对象.
    //也可以通过@OntToOne的fetch 属性来修改加载策略,变成代理对象
    @Test
    public void testOneToOneFind(){
        Manager mgr = entityManager.find(Manager.class, 1L);
        System.out.println(mgr.getMgrName());
        System.out.println(mgr.getDept().getClass().getName());
    }

    //1. 若获取不维护关联关系的一方, 则也会通过左外连接获取其关联的对象.
    //通过fetch 属性来修改加载策略,但依然会再发送SQL语句来初始化其关联的对象,修改无效
    @Test
    public void testOneToOneFind2(){
        Dept dept = entityManager.find(Dept.class, 2L);
        System.out.println(dept.getDname());
        System.out.println(dept.getMgr().getClass().getName());
    }



}
