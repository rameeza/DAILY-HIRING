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
	
	private Integer jobIdInFuseki;
	private String employerEmail;
	private String appointmentDescription;

	private static Integer jobOfferId = (int)(Math.random()*100000); 
	
	@NotNull
	private Integer jobOpenings;
	private Integer jobOpeningsAlreadyFilled;	
	private String datePosted; // date on which job was posted
	@NotNull
	private String jobStarts; // date when the job starts
	
	@NotNull
	private String jobEnds; // date when the job starts

	private String paymentType; 
	private String paymentMode; // in which payment will be made
	
	@NotNull
	private Double ratingRequired; // 0 - 5 
	

	/*
	 * @OneToOne (mappedBy = "jobOffer") private Employer employer; // todo - make
	 * it bidirectonal mapping
	 * 
	 * public Employer getEmployer() { return employer; } public void
	 * setEmployer(Employer employer) { this.employer = employer; }
	 */
	
	
	
	

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Employer employer;

	
	// Constructor used by Service implementation using Fuseki
	public JobOffer(Integer jobIdInFuseki, String appointmentDescription, @NotNull Integer jobOpenings,
			Integer jobOpeningsAlreadyFilled, @NotNull String jobStarts, @NotNull String jobEnds, String paymentMode,
			String paymentType, String employerEmail, @NotNull Double ratingRequired) {
		super();
		this.jobIdInFuseki = jobIdInFuseki;
		this.appointmentDescription = appointmentDescription;
		this.jobOpenings = jobOpenings;
		this.jobOpeningsAlreadyFilled = jobOpeningsAlreadyFilled;
		this.jobStarts = jobStarts;
		this.jobEnds = jobEnds;
		this.paymentMode = paymentMode;
		this.paymentType = paymentType;
		this.employerEmail = employerEmail;
		this.ratingRequired = ratingRequired;
	}

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

	
	public String getEmployerEmail() {
		return employerEmail;
	}

	public void setEmployerEmail(String employerEmail) {
		this.employerEmail = employerEmail;
	}

	public Double getRatingRequired() {
		return ratingRequired;
	}

	public void setRatingRequired(Double ratingRequired) {
		this.ratingRequired = ratingRequired;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
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

	public Integer getJobOpeningsAlreadyFilled() {
		return jobOpeningsAlreadyFilled;
	}

	public void setJobOpeningsAlreadyFilled(Integer jobOpeningsAlreadyFilled) {
		this.jobOpeningsAlreadyFilled = jobOpeningsAlreadyFilled;
	}

	@ManyToMany (fetch = FetchType.LAZY, 
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Worker> applicantWorkers;

	public JobOffer() {
		super();
		this.jobIdInFuseki = this.jobOfferId++; 

	}

	public JobOffer(String appointmentDescription, String responsibility, Integer jobOpenings, String datePosted, Double workHours,
			String validThrough, String paymentType, Double ratingRequired, Integer recommendation,
			Double competencyLevel, Education education) {
		super();
		this.appointmentDescription = appointmentDescription;
		this.jobOpenings = jobOpenings;
		this.datePosted = datePosted;
		this.jobStarts = validThrough;
		this.paymentType = paymentType;
		this.ratingRequired = ratingRequired;
		this.education = education;
	}

	public String getAppointmentDescription() {
		return appointmentDescription;
	}

	public void setAppointmentDescription(String appointmentDescription) {
		this.appointmentDescription = appointmentDescription;
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


	
	
	public Integer getJobIdInFuseki() {
		return jobIdInFuseki;
	}

	public void setJobIdInFuseki(Integer jobIdInFuseki) {
		this.jobIdInFuseki = jobIdInFuseki;
	}

	public static Integer getJobOfferId() {
		return jobOfferId;
	}

	public static void setJobOfferId(Integer jobOfferId) {
		JobOffer.jobOfferId = jobOfferId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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

	
	public String getJobStarts() {
		return jobStarts;
	}

	public void setJobStarts(String jobStarts) {
		this.jobStarts = jobStarts;
	}

	public String getJobEnds() {
		return jobEnds;
	}

	public void setJobEnds(String jobEnds) {
		this.jobEnds = jobEnds;
	}

	public Double getExperienceYears() {
		return ratingRequired;
	}

	public void setExperienceYears(Double ratingRequired) {
		this.ratingRequired = ratingRequired;
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
		this.jobOpeningsAlreadyFilled++;
	}
}
