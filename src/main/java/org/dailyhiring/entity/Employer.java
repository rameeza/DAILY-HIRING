package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Employer extends Person {
	@OneToOne
	private JobOffer jobOffer; // provides this job offer. todo- check if it is one to many

	public Employer() {
	}

	public Employer(String organisation, String name, Integer age, String gender, String email, String phoneNo,
			String address, String idProof) {
		super(organisation, name, age, gender, email, phoneNo, address, idProof);
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
	
}
