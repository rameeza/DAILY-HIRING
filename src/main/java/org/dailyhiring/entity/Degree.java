package org.dailyhiring.entity;

import javax.persistence.Entity;

@Entity
public class Degree extends Education{
	private String name;
	private String yearOfPassing;
	private String nameOfCollege;

	public Degree() {
		super();
	}

	public Degree(Integer yearsOfEducation, String name, String yearOfPassing, String nameOfCollege) {
		super( yearsOfEducation);
		this.name = name;
		this.yearOfPassing = yearOfPassing;
		this.nameOfCollege = nameOfCollege;
	}

	public String getNameOfCollege() {
		return nameOfCollege;
	}

	public void setNameOfCollege(String nameOfCollege) {
		this.nameOfCollege = nameOfCollege;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYearOfPassing() {
		return yearOfPassing;
	}

	public void setYearOfPassing(String yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

}
