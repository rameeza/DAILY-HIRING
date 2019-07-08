package org.dailyhiring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Worker extends Person {
	private Double costPerUnit;
	private String currency;
	// todo- make it unique
	// private Integer workerId; // it is unique. so, we can use the 'id' field of person class for this.
	private String jobTitle;
	private Double experienceYears;	// years
	private Integer recommendation; // 
	private Double competencyLevel;

	@OneToOne(cascade = {CascadeType.ALL})
	private Skill skill;
	@OneToOne(cascade = {CascadeType.ALL})
	private Certificate certificate; // todo- see if it will be one to many

	@OneToOne(cascade = {CascadeType.ALL})
	private Education education; // has this education
	
	public Worker() {
		super();
	}


	public Worker(Double costPerUnit, String currency, String jobTitle, Double experienceYears,
			Integer recommendation) {
		super();
		this.costPerUnit = costPerUnit;
		this.currency = currency;
		this.jobTitle = jobTitle;
		this.experienceYears = experienceYears;
		this.recommendation = recommendation;
	}

	public Double getCostPerUnit() {
		return costPerUnit;
	}
	public void setCostPerUnit(Double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public Double getexperienceYears() {
		return experienceYears;
	}
	public void setexperienceYears(Double experienceYears) {
		this.experienceYears = experienceYears;
	}

	public Integer getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Integer recommendation) {
		this.recommendation = recommendation;
	}

	public Skill getSkill() {
		return skill;
	}
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	public Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	public Education getEducation() {
		return education;
	}
	public void setEducation(Education education) {
		this.education = education;
	}


	public Double getExperienceYears() {
		return experienceYears;
	}


	public void setExperienceYears(Double experienceYears) {
		this.experienceYears = experienceYears;
	}


	public Double getCompetencyLevel() {
		if (this.education == null) {
			return this.recommendation + this.experienceYears;
		}
		return this.recommendation + this.experienceYears + this.education.getYearsOfEducation();
	}

	public void setCompetencyLevel(Double competencyLevel) {
		this.competencyLevel = competencyLevel;
	}

}
