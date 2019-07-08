package org.dailyhiring.service;

import java.util.List;

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
	public JobOffer save(JobOffer jobOffer) {
		JobOffer tempJobOffer = jobOfferRepository.save(jobOffer);
		return tempJobOffer;
	}

	@Override
	public List<JobOffer> findAll() {
		return jobOfferRepository.findAllByOrderByJobIdAsc();
	}
	
}
