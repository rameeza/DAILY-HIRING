package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Employer extends Person {
	
	private String currency;
	@OneToOne
	private JobOffer jobOffer; // provides this job offer. todo- check if it is one to many

	public Employer() {
	}

	public Employer(String organisation, String firstName, String lastName, Integer age, String gender, String email,
			String phoneNo, String address, String idProof, String currency ) {
		super(organisation, firstName, lastName, age, gender, email, phoneNo, address, idProof);
		this.currency = currency;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
