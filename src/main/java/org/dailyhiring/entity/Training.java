package org.dailyhiring.entity;

import javax.persistence.Entity;

@Entity
public class Training extends Education{
	private String name;
	private Double period; // years
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

	public Double getPeriod() {
		return period;
	}

	public void setPeriod(Double period) {
		this.period = period;
	}

	public Training(String name, Double period, String nameOfCompanyOrInstitute) {
		super();
		this.name = name;
		this.period = period;
		this.nameOfCompanyOrInstitute = nameOfCompanyOrInstitute;
	}

}
