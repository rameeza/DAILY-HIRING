package org.dailyhiring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dailyhiring.entity.JobOffer;
import org.springframework.stereotype.Service;

@Service
public interface JobOfferService {
	
	public JobOffer save(JobOffer jobOffer);

	public List<JobOffer> findAll();

	/* public List<JobOffer> findAllJobsMatchingFieldOfWork(int theWorkerId); */

	/* public List<JobOffer> findAllJobsMatchingCertificate(int theWorkerId); */
	
	public List<JobOffer> findAllMatchingJobs(int theWorkerId);

	public JobOffer save(@Valid JobOffer jobOffer, int employerId);

	public List<JobOffer> findAllJobsPostedBy(Integer employerId);

	public List<JobOffer> findAllJobsAppliedBy(Integer workerId);

	public List<JobOffer> findAllMatchingJobs(String theWorkerEmail, HttpServletRequest request);

}
