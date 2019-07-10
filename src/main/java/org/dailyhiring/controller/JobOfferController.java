package org.dailyhiring.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobOfferController {

	@Autowired
	private JobOfferService jobOfferService;

	/*
	 * @Autowired public JobOfferController(JobOfferService jobOfferService) {
	 * this.jobOfferService = jobOfferService; }
	 */

	/*
	 * @GetMapping("/postJobOffer") public String showJobOfferPostForm(JobOffer
	 * jobOffer) { return "joboffer/job-offer-post-form"; }
	 * 
	 */

	@GetMapping("/postJobOffer")
	public String showJobOfferPostForm(Model theModel) {
		// set initial values in job-post-form
		JobOffer jobOffer = new JobOffer();
		jobOffer.setexperienceYears(10.0); jobOffer.setJobTitle("daily worker");
		theModel.addAttribute("jobOffer", jobOffer);

		// return view
		return "joboffer/job-offer-post-form";
	}

	@GetMapping("/showAllJobs")
	public String showAllJobs(Model theModel) {
		// get jobs from db
		List<JobOffer> theJobOffers = jobOfferService.findAll();

		// add to the spring model
		theModel.addAttribute("jobs", theJobOffers);

		return "joboffer/show-all-jobs";
	}

	@GetMapping("/showJobsMatchingFieldOfWork")
	public String showJobsMatchingFieldOfWork(@RequestParam("workerId") int theWorkerId, Model theModel) {

		// get jobs from db
		List<JobOffer> theJobOffers = jobOfferService.findAllJobsMatchingFieldOfWork(theWorkerId);

		// add jobs to the spring model
		theModel.addAttribute("jobs", theJobOffers);

		return "joboffer/show-all-jobs";
	}

	@GetMapping("/showJobsMatchingCertificate")
	public String showJobsMatchingCertificate(@RequestParam("workerId") int theWorkerId, Model theModel) {

		// get jobs from db
		List<JobOffer> theJobOffers = jobOfferService.findAllJobsMatchingCertificate(theWorkerId);

		// add jobs to the spring model
		theModel.addAttribute("jobs", theJobOffers);

		return "joboffer/show-all-jobs";
	}

	@PostMapping("/postJobOffer")
	private String postJobOffer(@Valid JobOffer jobOffer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "joboffer/job-offer-post-form";
		}

		JobOffer tempJobOffer = jobOfferService.save(jobOffer);
		if (tempJobOffer == null) {
			return "joboffer/job-offer-post-failure";
		}
		return "joboffer/job-offer-post-successful";
	}
}