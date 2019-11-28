package org.dailyhiring.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Worker extends Person {
	private Double defaultPayVisit;
	@OneToOne(cascade = {CascadeType.ALL})
	private Skill skillType;
	private Double experience;	// years
	private Double payAmount;	
	private String typeOfPayAmount;
	private String locality;
	private Boolean hasCertificate;
	
	public Worker() {
		super();
	}

	public Worker(Double defaultPayVisit, Skill skillType, Double experience, Double payAmount, String typeOfPayAmount,
			String locality, Boolean hasCertificate) {
		super();
		this.defaultPayVisit = defaultPayVisit;
		this.skillType = skillType;
		this.experience = experience;
		this.payAmount = payAmount;
		this.typeOfPayAmount = typeOfPayAmount;
		this.locality = locality;
		this.hasCertificate = hasCertificate;
	}

	public Skill getSkill() {
		return skillType;
	}
	public void setSkill(Skill skill) {
		this.skillType = skill;
	}

	public Double getDefaultPayVisit() {
		return defaultPayVisit;
	}

	public void setDefaultPayVisit(Double defaultPayVisit) {
		this.defaultPayVisit = defaultPayVisit;
	}

	public Skill getSkillType() {
		return skillType;
	}

	public void setSkillType(Skill skillType) {
		this.skillType = skillType;
	}

	public Double getExperience() {
		return experience;
	}

	public void setExperience(Double experience) {
		this.experience = experience;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getTypeOfPayAmount() {
		return typeOfPayAmount;
	}

	public void setTypeOfPayAmount(String typeOfPayAmount) {
		this.typeOfPayAmount = typeOfPayAmount;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public Boolean getHasCertificate() {
		return hasCertificate;
	}

	public void setHasCertificate(Boolean hasCertificate) {
		this.hasCertificate = hasCertificate;
	}
	
}
