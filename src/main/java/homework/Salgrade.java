package homework;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author 王叔叔
 * @create 2020/10/17 13:41
 */
@Entity
public class Salgrade {
    private Long sid;
    private Long grade;
    private Long losal;
    private Long hisal;

    public Salgrade() {
    }

    public Salgrade(Long sid, Long grade, Long losal, Long hisal) {
        this.sid = sid;
        this.grade = grade;
        this.losal = losal;
        this.hisal = hisal;
    }

    @Id
    @Column(name = "SID")
    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    @Basic
    @Column(name = "GRADE")
    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "LOSAL")
    public Long getLosal() {
        return losal;
    }

    public void setLosal(Long losal) {
        this.losal = losal;
    }

    @Basic
    @Column(name = "HISAL")
    public Long getHisal() {
        return hisal;
    }

    public void setHisal(Long hisal) {
        this.hisal = hisal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Salgrade salgrade = (Salgrade) o;

        if (sid != salgrade.sid) return false;
        if (grade != null ? !grade.equals(salgrade.grade) : salgrade.grade != null) return false;
        if (losal != null ? !losal.equals(salgrade.losal) : salgrade.losal != null) return false;
        if (hisal != null ? !hisal.equals(salgrade.hisal) : salgrade.hisal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (sid ^ (sid >>> 32));
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (losal != null ? losal.hashCode() : 0);
        result = 31 * result + (hisal != null ? hisal.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Salgrade{" +
                "sid=" + sid +
                ", grade=" + grade +
                ", losal=" + losal +
                ", hisal=" + hisal +
                '}';
    }
}
