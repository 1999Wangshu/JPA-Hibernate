package com.pojo;

import javax.persistence.*;

@Table(name="MANAGERS")
@Entity
public class Manager {

	private long id;
	private String mgrName;
	
	private Dept dept;

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Basic
	@Column(name="MGR_NAME")
	public String getMgrName() {
		return mgrName;
	}

	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPTNO",unique = true)
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
}
