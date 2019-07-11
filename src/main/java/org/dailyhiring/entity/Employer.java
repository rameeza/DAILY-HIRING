package org.dailyhiring.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Employer extends Person {
	
	private String currency;

	/*
	@OneToOne
	private JobOffer jobOffer; // provides this job offer. todo- check if it is one to many

	
	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
	*/
	
	// refers to employer property in JobOffer class
	@OneToMany (mappedBy="employer",
			cascade= {CascadeType.ALL})
	private List<JobOffer> jobOffers;
	
	public List<JobOffer> getJobOffers() {
		return jobOffers;
	}

	public void setJobOffers(List<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public Employer() {
		
	}
	
	public Employer(String organisation, String firstName, String lastName, Integer age, String gender, String email,
			String phoneNo, String address, String idProof, String currency ) {
		super(organisation, firstName, lastName, age, gender, email, phoneNo, address, idProof);
		this.currency = currency;
	}

	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void add(JobOffer jobOffer) {
		if (jobOffers == null) {
			jobOffers = new ArrayList<JobOffer>();
		}
		// bidirectional link
		jobOffers.add(jobOffer);
		jobOffer.setEmployer(this);
	}
	
}
