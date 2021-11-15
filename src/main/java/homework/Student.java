package homework;

import javax.persistence.*;

/**
 * @author 王叔叔
 * @create 2020/10/17 18:07
 */
@Entity
public class Student {
    private long sid;
    private String sname;
    private String sex;
    private String brith;
    private String status;
    private Cclass cclass;

    public Student() {
    }

    public Student(long sid, String sname, String sex, String brith, String status, Cclass cclass) {
        this.sid = sid;
        this.sname = sname;
        this.sex = sex;
        this.brith = brith;
        this.status = status;
        this.cclass = cclass;
    }

    @Id
    @Column(name = "SID")
    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    @Basic
    @Column(name = "SNAME")
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Basic
    @Column(name = "SEX")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "BRITH")
    public String getBrith() {
        return brith;
    }

    public void setBrith(String brith) {
        this.brith = brith;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (sid != student.sid) return false;
        if (sname != null ? !sname.equals(student.sname) : student.sname != null) return false;
        if (sex != null ? !sex.equals(student.sex) : student.sex != null) return false;
        if (brith != null ? !brith.equals(student.brith) : student.brith != null) return false;
        if (status != null ? !status.equals(student.status) : student.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (sid ^ (sid >>> 32));
        result = 31 * result + (sname != null ? sname.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (brith != null ? brith.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "CID", referencedColumnName = "CID")
    public Cclass getCclass() {
        return cclass;
    }

    public void setCclass(Cclass cclass) {
        this.cclass = cclass;
    }


}
