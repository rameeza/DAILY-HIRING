package org.dailyhiring.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.entity.Worker;
import org.dailyhiring.service.EmployerServiceImplUsingJena;
import org.dailyhiring.service.JobOfferService;
import org.dailyhiring.service.JobOfferServiceImplUsingJena;
import org.dailyhiring.service.WorkerService;
import org.dailyhiring.service.WorkerServiceImplUsingJena;
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
		jobOffer.setRatingRequired(1.0);
		//jobOffer.setEducation(0.0);
		//jobOffer.setJobTitle("daily worker");
		theModel.addAttribute("jobOffer", jobOffer);

		// return view
		return "joboffer/job-offer-post-form-new";
	}

	@GetMapping("/showAllMatchingJobs")
	public String showAllMatchingJobs(@RequestParam("workerEmail") String theWorkerEmail, 
			Model theModel, HttpServletRequest request) {
		// get jobs from db
		//List<JobOffer> theJobOffers = jobOfferService.findAllMatchingJobs(theWorkerEmail);
		JobOfferServiceImplUsingJena jOSImplUsingJena = new JobOfferServiceImplUsingJena();
		// add jobs to the spring model
		List<JobOffer> theJobOffers = jOSImplUsingJena.findAllMatchingJobs(theWorkerEmail, request);
		System.out.println("theJobOffers is going to be added to the model.");
		theModel.addAttribute("jobs", theJobOffers);
		
		return "worker/show-all-matching-jobs";
	}

	@PostMapping("/showAllCustomMatchingJobs")
	public String showAllCustomMatchingJobs(//@RequestParam("distance") Integer theDistance, 
			Model theModel, HttpServletRequest request) {

		Integer distance = Integer.parseInt(
				request.getParameter("distance"));
		
		Double latitude = 999.0; // an invalid value
		if (!request.getParameter("latitude").equals("")) {
			latitude =  Double.parseDouble(
					request.getParameter("latitude"));
			
		}
		Double longitude = 999.0; // an invalid value
		if (!request.getParameter("longitude").equals("")) {
			longitude =  Double.parseDouble(
					request.getParameter("longitude"));
		}
		
		System.out.println("RZ >>>>>>>>>>>> Distance = " + distance);
		System.out.println("RZ >>>>>>>>>>>> Latitude = " + latitude);
		System.out.println("RZ >>>>>>>>>>>> Longitude = " + longitude);
		
		JobOfferServiceImplUsingJena jOSImplUsingJena = new JobOfferServiceImplUsingJena();
		
		String workerEmail = ((Worker)request.getSession().getAttribute("worker")).getEmail();
		List<JobOffer> theJobOffers = jOSImplUsingJena.findAllMatchingJobs(workerEmail, request, 
				distance, latitude, longitude);
		
		System.out.println("theJobOffers is going to be added to the model.");
		theModel.addAttribute("jobs", theJobOffers);
		
		return "worker/show-all-custom-matching-jobs";
	}

	
	
	/*
	 * @GetMapping("/showAllJobs") public String showAllJobs(Model theModel) { //
	 * get jobs from db List<JobOffer> theJobOffers = jobOfferService.findAll();
	 * System.out.println("----------->>>>>>" + theModel); // add to the spring
	 * model theModel.addAttribute("jobs", theJobOffers);
	 * 
	 * return "joboffer/show-all-jobs"; }
	 */
	
	
	/*
	 * @GetMapping("/showJobsMatchingFieldOfWork") public String
	 * showJobsMatchingFieldOfWork(@RequestParam("workerId") int theWorkerId, Model
	 * theModel) {
	 * 
	 * // get jobs from db List<JobOffer> theJobOffers =
	 * jobOfferService.findAllJobsMatchingFieldOfWork(theWorkerId);
	 * 
	 * // add jobs to the spring model theModel.addAttribute("jobs", theJobOffers);
	 * 
	 * return "joboffer/show-all-jobs"; }
	 */

	
	
	/*
	 * @GetMapping("/showJobsMatchingCertificate") public String
	 * showJobsMatchingCertificate(@RequestParam("workerId") int theWorkerId, Model
	 * theModel) {
	 * 
	 * // get jobs from db List<JobOffer> theJobOffers =
	 * jobOfferService.findAllJobsMatchingCertificate(theWorkerId);
	 * 
	 * // add jobs to the spring model theModel.addAttribute("jobs", theJobOffers);
	 * 
	 * return "joboffer/show-all-jobs"; }
	 */
	
	@PostMapping("/postJobOffer")
	private String postJobOffer(@Valid JobOffer jobOffer, BindingResult bindingResult, 
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			System.out.println("Inside bindingResult.hasErrors()");
			return "joboffer/job-offer-post-form-new";	
		}
		jobOffer.setJobOpeningsAlreadyFilled(0);
		//jobOffer.setDatePosted(new Date().toString());
		jobOffer.setEmployerEmail(request.getSession().
				getAttribute("employerEmail").toString());
		
		
		JobOfferServiceImplUsingJena jsj = new JobOfferServiceImplUsingJena();
		if (jsj.save(jobOffer) != null) {
			return "joboffer/job-offer-post-successful";
		} else {
			return "joboffer/job-offer-post-failure";
		}

	}

	
	@GetMapping("/applyInThisJob")
	public String applyInThisJob(@RequestParam("jobIdInFuseki") Integer theJobId, Model theModel,
			HttpServletRequest request) {

		//System.out.println(">>>>>>>>>>>>>> " + this.getClass() + " applyInThisJob() starts ");

		String workerEmail = (String)request.getSession().getAttribute("workerEmail");
		
		System.out.println(">>>>>>>>>>>>>>>>>> Worker email: " + workerEmail);
		System.out.println(">>>>>>>>>>>>>>>>>> This worker will" + " apply in Job now");

		WorkerServiceImplUsingJena wSIUsingJena = new WorkerServiceImplUsingJena();
		wSIUsingJena.applyInJob(workerEmail, theJobId, request);

		// get jobs from db List<JobOffer> theJobOffers =
		JobOfferServiceImplUsingJena jOSIUsingJena = new JobOfferServiceImplUsingJena();
		List<JobOffer> theJobOffers = jOSIUsingJena.findAllMatchingJobs(workerEmail, request);

		// add jobs to the spring model
		theModel.addAttribute("jobs", theJobOffers);

		// add jobs to the spring model theModel.addAttribute("jobs", theJobOffers);
		return "worker/show-all-matching-jobs";
	}

}