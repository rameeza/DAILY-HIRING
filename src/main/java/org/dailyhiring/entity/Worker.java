package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Worker extends Person {
	private Double costPerUnit;
	private String currency;
	// todo- make it unique
	private Integer workerId;
	private String jobTitle;
	private Double experience;	// years
	private String recommendation; // todo- ask what is it
	private String competencyLevel;

	@OneToOne
	private Skill skill;
	@OneToOne
	private Certificate certificate; // todo- see if it will be one to many

	@OneToOne
	private Education education; // has this education
	
	public Worker() {
		super();
	}
	public Worker(String organisation, String name, Integer age, String gender, String email, String phoneNo,
			String address, String idProof,
			Double costPerUnit, String currency, String jobTitle, Double experience, String recommendation,
			String competencyLevel) {
		super(organisation, name, age, gender, email, phoneNo, address, idProof);
		this.costPerUnit = costPerUnit;
		this.currency = currency;
		this.jobTitle = jobTitle;
		this.experience = experience;
		this.recommendation = recommendation;
		this.competencyLevel = competencyLevel;
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
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public Double getExperience() {
		return experience;
	}
	public void setExperience(Double experience) {
		this.experience = experience;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getCompetencyLevel() {
		return competencyLevel;
	}
	public void setCompetencyLevel(String competencyLevel) {
		this.competencyLevel = competencyLevel;
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
	
}
