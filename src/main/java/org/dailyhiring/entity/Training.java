package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Training extends Education{
	private String name;
	private String nameOfCompanyOrInstitute;

	public Training() {
		super();
	}

	
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


	public Training(String name, String nameOfCompanyOrInstitute) {
		this.name = name;
		this.nameOfCompanyOrInstitute = nameOfCompanyOrInstitute;
	}

}
