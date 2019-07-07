package org.dailyhiring.service;

import org.dailyhiring.dao.JobOfferRepository;
import org.dailyhiring.entity.JobOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobOfferServiceImpl implements JobOfferService {
	
	private JobOfferRepository jobOfferRepository;

	@Autowired
	public JobOfferServiceImpl(JobOfferRepository jobOfferRepository) {
		this.jobOfferRepository = jobOfferRepository;
	}

	@Override
	public void save(JobOffer jobOffer) {
		jobOfferRepository.save(jobOffer);
		
	}
	
}
