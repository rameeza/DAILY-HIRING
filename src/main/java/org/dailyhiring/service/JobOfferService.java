package org.dailyhiring.service;

import java.util.List;

import org.dailyhiring.entity.JobOffer;

public interface JobOfferService {
	
	public JobOffer save(JobOffer jobOffer);

	public List<JobOffer> findAll();

}
