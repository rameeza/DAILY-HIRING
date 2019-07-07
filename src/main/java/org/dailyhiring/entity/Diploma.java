package org.dailyhiring.entity;

import javax.persistence.Entity;

@Entity
public class Diploma extends Education{
	private String name;
	private String nameOfCollege;
	private Double yearOfPassing;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameOfCollege() {
		return nameOfCollege;
	}

	public void setNameOfCollege(String nameOfCollege) {
		this.nameOfCollege = nameOfCollege;
	}

	public Double getYearOfPassing() {
		return yearOfPassing;
	}

	public void setYearOfPassing(Double yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

	public Diploma(String name, String nameOfCollege, Double yearOfPassing) {
		super();
		this.name = name;
		this.nameOfCollege = nameOfCollege;
		this.yearOfPassing = yearOfPassing;
	}

	public Diploma() {
		super();
	}

}
