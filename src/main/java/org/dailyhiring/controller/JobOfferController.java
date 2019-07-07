package org.dailyhiring.controller;

import javax.validation.Valid;

import org.dailyhiring.entity.Degree;
import org.dailyhiring.entity.Education;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.entity.test.Worker2;
import org.dailyhiring.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobOfferController {
	
	private JobOfferService jobOfferService;
	@Autowired
	public JobOfferController(JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	@GetMapping("/postJobOffer")
	public String showJobOfferPostForm(JobOffer jobOffer) {
		return "joboffer/job-offer-post-form";
	}	
	
	@PostMapping("/postJobOffer")
	private String postJobOffer(@Valid JobOffer jobOffer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "joboffer/job-offer-post-form";
		}
		
		/*
		 * Education ed = new Degree (); Education ed2 = new Degree(5, "class 5",
		 * "2015", "smvdu"); JobOffer jobOffer = new JobOffer("watchman", "stay awake",
		 * 10, "2018-10-10", 10.0, "2018-12-12", "Dollar", 2.5, 3, 8.0, ed);
		 */		
		JobOffer tempJobOffer = jobOfferService.save(jobOffer);
		if (tempJobOffer == null) {
			return "joboffer/job-offer-post-failure";
		}
		return "joboffer/job-offer-post-successful";
	}
}