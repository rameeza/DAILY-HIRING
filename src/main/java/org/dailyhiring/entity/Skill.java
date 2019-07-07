package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Skill {
	private String name;

	@OneToOne
	private FieldOfWork fieldOfWork;
	@OneToOne
	private PayBy payBy; // todo- check if it can be one to many
	
	@Id @GeneratedValue
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Skill() {
		super();
	}

	public String getName() {
		return name;
	}

	public Skill(String name) {
		super();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldOfWork getFieldOfWork() {
		return fieldOfWork;
	}

	public void setFieldOfWork(FieldOfWork fieldOfWork) {
		this.fieldOfWork = fieldOfWork;
	}

	public PayBy getPayBy() {
		return payBy;
	}

	public void setPayBy(PayBy payBy) {
		this.payBy = payBy;
	}

	public Skill(String name, FieldOfWork fieldOfWork, PayBy payBy) {
		super();
		this.name = name;
		this.fieldOfWork = fieldOfWork;
		this.payBy = payBy;
	}
	
}
