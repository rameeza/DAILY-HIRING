package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Education {
	
	@Id @GeneratedValue
	private Integer id;
	
	private Integer yearsOfEducation;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYearsOfEducation() {
		return yearsOfEducation;
	}

	public void setYearsOfEducation(Integer yearsOfEducation) {
		this.yearsOfEducation = yearsOfEducation;
	}

	public Education(Integer yearsOfEducation) {
		super();
		this.yearsOfEducation = yearsOfEducation;
	}

	public Education() {
		super();
	}
	
}