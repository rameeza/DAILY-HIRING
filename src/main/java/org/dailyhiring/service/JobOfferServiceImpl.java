package org.dailyhiring.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.dailyhiring.dao.JobOfferRepository;
import org.dailyhiring.dao.WorkerRepository;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.entity.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobOfferServiceImpl implements JobOfferService {

	private JobOfferRepository jobOfferRepository;
	@Autowired
	private WorkerRepository workerRepository;

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

	@Override
	public List<JobOffer> findAllJobsMatchingFieldOfWork(int theWorkerId) {
		List<JobOffer> jobOffers = jobOfferRepository.findAllByOrderByJobIdAsc();
		Optional<Worker> optionalWorker = workerRepository.findById(theWorkerId);
		Worker worker = optionalWorker.get();

		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();
			if (!(nextJobOffer.getFieldOfWork().getName().equals(worker.getSkill().getFieldOfWork().getName()))) {
				iterator.remove();
			}
		}
		return jobOffers;
	}


	@Override
	public List<JobOffer> findAllJobsMatchingCertificate(int theWorkerId) {
		List<JobOffer> jobOffers = jobOfferRepository.findAllByOrderByJobIdAsc();
		Optional<Worker> optionalWorker = workerRepository.findById(theWorkerId);
		Worker worker = optionalWorker.get();

		for (Iterator<JobOffer> iterator = jobOffers.iterator(); iterator.hasNext();) {
			JobOffer nextJobOffer = iterator.next();
			if (!(nextJobOffer.getCertificate().getName().equals(worker.getCertificate().getName()))) {
				iterator.remove();
			}
		}
		return jobOffers;
	}
	
}
