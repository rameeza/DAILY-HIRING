package org.dailyhiring.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.entity.Worker;
import org.dailyhiring.service.JobOfferService;
import org.dailyhiring.service.WorkerService;
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
	
	@Autowired
	private WorkerService workerService;	

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
		jobOffer.setexperienceYears(10.0);
		jobOffer.setJobTitle("daily worker");
		theModel.addAttribute("jobOffer", jobOffer);

		// return view
		return "joboffer/job-offer-post-form";
	}

	@GetMapping("/showAllMatchingJobs")
	public String showAllMatchingJobs(@RequestParam("workerId") int theWorkerId, Model theModel) {
		// get jobs from db
		List<JobOffer> theJobOffers = jobOfferService.findAllMatchingJobs(theWorkerId);

		// add jobs to the spring model
		theModel.addAttribute("jobs", theJobOffers);
		return "joboffer/show-all-jobs";
	}

	@GetMapping("/showAllJobs")
	public String showAllJobs(Model theModel) {
		// get jobs from db
		List<JobOffer> theJobOffers = jobOfferService.findAll();
		System.out.println("----------->>>>>>" + theModel);
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
	private String postJobOffer(@Valid JobOffer jobOffer, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "joboffer/job-offer-post-form";
		}

		JobOffer tempJobOffer = jobOfferService.save(jobOffer,
				Integer.parseInt(request.getSession().getAttribute("employerId").toString()));
		if (tempJobOffer == null) {
			return "joboffer/job-offer-post-failure";
		}
		return "joboffer/job-offer-post-successful";
	}

	@GetMapping("/applyInThisJob")
	public String applyInThisJob(@RequestParam("jobId") Integer theJobId, Model theModel,
			HttpServletRequest request) {

		System.out.println(">>>>>>>>>>>>>> " + this.getClass() + " spplyInThisJob() starts ");
		
		  Worker workerInSession = (Worker)request.getSession().getAttribute("worker");
		  Integer workerId = workerInSession.getId();
		  
		  System.out.println(">>>>>>>>>>>>>>>>>> Worker id: " + workerId); System.out.
		  println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> This worker will" +
		  " apply in Job now");
		  
		  workerService.applyInJob(workerId, theJobId);
		  
		  // get jobs from db List<JobOffer> theJobOffers =
		  List<JobOffer> theJobOffers = jobOfferService.findAllMatchingJobs(workerId);
		  
		  
		  // add jobs to the spring model
		  theModel.addAttribute("jobs", theJobOffers);
		  
		  // add jobs to the spring model theModel.addAttribute("jobs", theJobOffers);
		 		return "joboffer/show-all-jobs";
	}

}