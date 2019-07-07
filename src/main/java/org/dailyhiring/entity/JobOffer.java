package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class JobOffer {
	@Id @GeneratedValue
	private String jobId;
	private String jobTitle;
	private String responsibility;
	private Integer jobOpenings; // todo- ask if it is no of openings
	private String datePosted;
	private Double workHours;
	private String validity; // todo- ask what it is
	private String currency; // todo- ask what is it
	private Double experience; // in years 

	@OneToOne (mappedBy = "jobOffer")
	private Employer employer; // todo - make it bidirectonal mapping 
	@OneToOne
	private FieldOfWork fieldOfWork; // offers this field of work
	
	@OneToOne
	private Certificate certificate; // requires this certificate
	
	@OneToOne
	private Education education; // requires this education
	
	public JobOffer() {
		super();
	}
	public JobOffer(String jobTitle, String responsibility, Integer jobOpenings, String datePosted, Double workHours,
			String validity, String currency) {
		super();
		this.jobTitle = jobTitle;
		this.responsibility = responsibility;
		this.jobOpenings = jobOpenings;
		this.datePosted = datePosted;
		this.workHours = workHours;
		this.validity = validity;
		this.currency = currency;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	public Integer getJobOpenings() {
		return jobOpenings;
	}
	public void setJobOpenings(Integer jobOpenings) {
		this.jobOpenings = jobOpenings;
	}
	public String getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}
	public Double getWorkHours() {
		return workHours;
	}
	public void setWorkHours(Double workHours) {
		this.workHours = workHours;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getExperience() {
		return experience;
	}
	public void setExperience(Double experience) {
		this.experience = experience;
	}
	public FieldOfWork getFieldOfWork() {
		return fieldOfWork;
	}
	public void setFieldOfWork(FieldOfWork fieldOfWork) {
		this.fieldOfWork = fieldOfWork;
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
	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	
}
