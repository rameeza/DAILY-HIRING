package org.dailyhiring.controller;

import org.dailyhiring.entity.Degree;
import org.dailyhiring.entity.Education;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobOfferController {
	
	private JobOfferService jobOfferService;
	@Autowired
	public JobOfferController(JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	@GetMapping("/postJobOffer")
	private String postJobOffer() {
		Education ed = new Degree ();
		Education ed2 = new Degree(5, "class 5", "2015", "smvdu");
		JobOffer jobOffer = new JobOffer("watchman", "stay awake", 10, "2018-10-10", 10.0,
				"2018-12-12", "Dollar", 2.5, 3, 8.0, ed);
		jobOfferService.save(jobOffer);
		return "loginSuccessful";
		
	}

}
