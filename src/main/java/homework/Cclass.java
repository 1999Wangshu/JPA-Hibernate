package homework;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 王叔叔
 * @create 2020/10/17 18:07
 */
@Entity
public class Cclass {
    private long cid;
    private String cname;
    private Timestamp ctime;
    private Long cnum;
    private List<Student> students;

    public Cclass() {
    }

    public Cclass(long cid, String cname, Timestamp ctime, Long cnum) {
        this.cid = cid;
        this.cname = cname;
        this.ctime = ctime;
        this.cnum = cnum;
    }

    @Id
    @Column(name = "CID")
    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "CNAME")
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Basic
    @Column(name = "CTIME")
    public Timestamp getCtime() {
        return ctime;
    }

    public void setCtime(Timestamp ctime) {
        this.ctime = ctime;
    }

    @Basic
    @Column(name = "CNUM")
    public Long getCnum() {
        return cnum;
    }

    public void setCnum(Long cnum) {
        this.cnum = cnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cclass cclass = (Cclass) o;

        if (cid != cclass.cid) return false;
        if (cname != null ? !cname.equals(cclass.cname) : cclass.cname != null) return false;
        if (ctime != null ? !ctime.equals(cclass.ctime) : cclass.ctime != null) return false;
        if (cnum != null ? !cnum.equals(cclass.cnum) : cclass.cnum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (cid ^ (cid >>> 32));
        result = 31 * result + (cname != null ? cname.hashCode() : 0);
        result = 31 * result + (ctime != null ? ctime.hashCode() : 0);
        result = 31 * result + (cnum != null ? cnum.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cclass")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {

        this.students = students;
    }

    @Override
    public String toString() {
        return "Cclass{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", ctime=" + ctime +
                ", cnum=" + cnum +
                ", students=" + students +
                '}';
    }
}
