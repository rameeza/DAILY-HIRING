package org.dailyhiring.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class JobOffer {
	@Id
	@GeneratedValue
	private Integer jobId;
	private String jobTitle;
	private String responsibility; // refers to all responsibilities associated with job.
	@NotNull
	private Integer jobOpenings;
	private String datePosted; // date on which job was posted
	@NotNull
	private Double workHours; // no. of hours of work required per day.
	@NotNull
	private String validThrough; // date up to which the job is valid
	private String currency; // in which payment wll be made
	@NotNull
	private Double experienceYears; // years
	private Integer recommendation; //
	private Double competencyLevel;

	/*
	 * @OneToOne (mappedBy = "jobOffer") private Employer employer; // todo - make
	 * it bidirectonal mapping
	 * 
	 * public Employer getEmployer() { return employer; } public void
	 * setEmployer(Employer employer) { this.employer = employer; }
	 */

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Employer employer;

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	@OneToOne(cascade = { CascadeType.ALL })
	private FieldOfWork fieldOfWork; // offers this field of work

	@OneToOne(cascade = { CascadeType.ALL })
	private Certificate certificate; // requires this certificate

	@OneToOne(cascade = { CascadeType.ALL })
	private Degree degree; // requires this degree

	@OneToOne(cascade = { CascadeType.ALL })
	private Diploma diploma; // requires this education

	@OneToOne(cascade = { CascadeType.ALL })
	private Training training; // requires this education

	@OneToOne(cascade = { CascadeType.ALL })
	private Education education; // requires this education
	
	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public Diploma getDiploma() {
		return diploma;
	}

	public void setDiploma(Diploma diploma) {
		this.diploma = diploma;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	@ManyToMany (fetch = FetchType.LAZY, 
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Worker> applicantWorkers;

	public JobOffer() {
		super();

	}

	public JobOffer(String jobTitle, String responsibility, Integer jobOpenings, String datePosted, Double workHours,
			String validThrough, String currency, Double experienceYears, Integer recommendation,
			Double competencyLevel, Education education) {
		super();
		this.jobTitle = jobTitle;
		this.responsibility = responsibility;
		this.jobOpenings = jobOpenings;
		this.datePosted = datePosted;
		this.workHours = workHours;
		this.validThrough = validThrough;
		this.currency = currency;
		this.experienceYears = experienceYears;
		this.recommendation = recommendation;
		this.competencyLevel = competencyLevel;
		this.education = education;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getexperienceYears() {
		return experienceYears;
	}

	public void setexperienceYears(Double experienceYears) {
		this.experienceYears = experienceYears;
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

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getValidThrough() {
		return validThrough;
	}

	public void setValidThrough(String validThrough) {
		this.validThrough = validThrough;
	}

	public Double getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(Double experienceYears) {
		this.experienceYears = experienceYears;
	}

	public Integer getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Integer recommendation) {
		this.recommendation = recommendation;
	}

	public Double getCompetencyLevel() {
		return competencyLevel;
	}

	public void setCompetencyLevel(Double competencyLevel) {
		this.competencyLevel = competencyLevel;
	}

	public List<Worker> getApplicantWorkers() {
		return applicantWorkers;
	}

	public void setApplicantWorkers(List<Worker> applicantWorkers) {
		this.applicantWorkers = applicantWorkers;
	}
	
	// convenience method
	public void addApplicantWorker(Worker worker) {
		if (applicantWorkers == null) {
			applicantWorkers = new ArrayList<Worker>();
		}
		applicantWorkers.add(worker);
	}
}
