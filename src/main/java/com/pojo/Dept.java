package com.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 王叔叔
 * @create 2020/10/12 19:03
 */
@Entity
public class Dept implements Serializable {
    private long deptno;
    private String dname;
    private String loc;
    private List<Emp> emps;

    private Manager mgr;

    public Dept() {
    }

    public Dept(long deptno, String dname, String loc) {
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }

    @Id
    @Column(name = "DEPTNO")
    public long getDeptno() {
        return deptno;
    }

    public void setDeptno(long deptno) {
        this.deptno = deptno;
    }

    @Basic
    @Column(name = "DNAME")
    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }


    @Basic
    @Column(name = "LOC")
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dept dept = (Dept) o;

        if (deptno != dept.deptno) return false;
        if (dname != null ? !dname.equals(dept.dname) : dept.dname != null) return false;
        if (loc != null ? !loc.equals(dept.loc) : dept.loc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (deptno ^ (deptno >>> 32));
        result = 31 * result + (dname != null ? dname.hashCode() : 0);
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        return result;
    }

    //使用mappedBy不能使用@JoinColumn()
    //mappedBy = "dept"设置主控方,让emps维护关联关系
    @OneToMany(mappedBy = "dept",fetch =FetchType.LAZY,cascade = CascadeType.REMOVE)
    public List<Emp> getEmps() {
        return emps;
    }

    public void setEmps(List<Emp> emps) {
        this.emps = emps;
    }

    //1对1,dept不维护关联关系
    //@PrimaryKeyJoinColumn基于主键关联
    @OneToOne(mappedBy = "dept")
    public Manager getMgr() {
        return mgr;
    }
    public void setMgr(Manager mgr) {
        this.mgr = mgr;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptno=" + deptno +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                ", emps=" + emps +
                '}';
    }
}
