package org.dailyhiring.dao;

import java.util.List;

import org.dailyhiring.entity.JobOffer;
import org.springframework.data.repository.CrudRepository;

public interface JobOfferRepository extends CrudRepository<JobOffer, Integer> {

	List<JobOffer> findAllByOrderByJobIdAsc();

}
