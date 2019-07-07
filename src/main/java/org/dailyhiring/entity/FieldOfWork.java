package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FieldOfWork {
	private String name;

	@Id @GeneratedValue
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FieldOfWork(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public FieldOfWork() {
		super();
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
