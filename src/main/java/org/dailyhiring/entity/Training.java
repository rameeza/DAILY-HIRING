package org.dailyhiring.entity;

import javax.persistence.Entity;

@Entity
public class Training extends Education{
	private String name;
	private String nameOfCompanyOrInstitute;
	
	public String getNameOfCompanyOrInstitute() {
		return nameOfCompanyOrInstitute;
	}

	public void setNameOfCompanyOrInstitute(String nameOfCompanyOrInstitute) {
		this.nameOfCompanyOrInstitute = nameOfCompanyOrInstitute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Training(Integer yearsOfEducation, String name, String nameOfCompanyOrInstitute) {
		super(yearsOfEducation);
		this.name = name;
		this.nameOfCompanyOrInstitute = nameOfCompanyOrInstitute;
	}

}
