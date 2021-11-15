package homework;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author 王叔叔
 * @create 2020/10/17 16:24
 */
@Entity
public class Person {
    private long pid;
    private String pname;
    private String pwd;
    private Timestamp registertime;
    private Long integral;

    public Person() {
    }

    public Person(long pid, String pname, String pwd, Timestamp registertime, Long integral) {
        this.pid = pid;
        this.pname = pname;
        this.pwd = pwd;
        this.registertime = registertime;
        this.integral = integral;
    }

    @Id
    @Column(name = "PID")
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "PNAME")
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Basic
    @Column(name = "PWD")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Basic
    @Column(name = "REGISTERTIME")
    public Timestamp getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Timestamp registertime) {
        this.registertime = registertime;
    }

    @Basic
    @Column(name = "INTEGRAL")
    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (pid != person.pid) return false;
        if (pname != null ? !pname.equals(person.pname) : person.pname != null) return false;
        if (pwd != null ? !pwd.equals(person.pwd) : person.pwd != null) return false;
        if (registertime != null ? !registertime.equals(person.registertime) : person.registertime != null)
            return false;
        if (integral != null ? !integral.equals(person.integral) : person.integral != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (pid ^ (pid >>> 32));
        result = 31 * result + (pname != null ? pname.hashCode() : 0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (registertime != null ? registertime.hashCode() : 0);
        result = 31 * result + (integral != null ? integral.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", registertime=" + registertime +
                ", integral=" + integral +
                '}';
    }
}
