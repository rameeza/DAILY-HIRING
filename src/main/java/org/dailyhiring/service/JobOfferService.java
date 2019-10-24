package org.dailyhiring.service;

import java.util.List;

import javax.validation.Valid;

import org.dailyhiring.entity.JobOffer;

public interface JobOfferService {
	
	public JobOffer save(JobOffer jobOffer);

	public List<JobOffer> findAll();

	public List<JobOffer> findAllJobsMatchingFieldOfWork(int theWorkerId);

	public List<JobOffer> findAllJobsMatchingCertificate(int theWorkerId);
	
	public List<JobOffer> findAllMatchingJobs(int theWorkerId);

	public JobOffer save(@Valid JobOffer jobOffer, int employerId);

}
