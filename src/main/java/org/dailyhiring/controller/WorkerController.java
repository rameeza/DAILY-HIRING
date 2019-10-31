package org.dailyhiring.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.entity.Education;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.entity.Worker;
import org.dailyhiring.service.JobOfferService;
import org.dailyhiring.service.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkerController {
	@Autowired
	private JobOfferService jobOfferService;
	
	@Autowired
	private WorkerService workerService;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	/*
	 * @Autowired public WorkerController(WorkerService workerService) {
	 * this.workerService = workerService; }
	 * 
	 */	
	
	@GetMapping("/loginWorker")
	public String showWorkerLoginForm(Worker worker) {
		return "worker/worker-login-form";
	}
	
	@PostMapping("/loginWorker")
	public String checkWorkerLoginInfo(@Valid Worker worker, 
			BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "worker/worker-login-form";
		}
		Worker tempWorker= workerService.
				findById(worker.getId());
		if (tempWorker == null) {
			return "worker/worker-login-failure";
		}
		if (tempWorker.getPassword().equals(worker.getPassword())) {
			request.getSession().setAttribute("workerEmail", tempWorker.getEmail());
			/*
				put worker in session as once redirected, it can't be 
				accessed using model. id of worker is required in home page.
			*/
			request.getSession().setAttribute("worker", tempWorker);
			request.getSession().setMaxInactiveInterval(300); // In seconds
			/*
			 PRG(post/redirect/get) pattern -> 
				redirect to avoid multiple form submissions on page refresh
			*/	
			return "redirect:/workerHomePage";		
		}
		return "worker/worker-login-failure";
	}


	@GetMapping("/workerHomePage")
	public String showHomePage() {
		return "worker/worker-home-page";
		
	}

	@GetMapping("/workerProfilePage")
	public String showWorkerProfilePage() {
		return "worker/worker-profile-page";
	}

	@GetMapping("/workerAssignedJobsPage")
	public String showWorkerAssignedJobsPage(HttpServletRequest request, Model theModel) {
		
		List<JobOffer> theJobOffers = jobOfferService.findAllJobsAppliedBy(
				((Worker)request.getSession().getAttribute("worker")).getId());

		// add jobs to the spring model
		theModel.addAttribute("jobs", theJobOffers);
		
		return "worker/worker-assigned-jobs-page";
	}
	
	@GetMapping("/workerAboutPage")
	public String showWorkerAboutPage() {
		return "worker/worker-about-page";
	}
	
	/*
	 * @GetMapping("/registerWorker") public String
	 * showWorkerRegistrationForm(Worker worker) {
	 * //worker.getEducation().setYearsOfEducation(0); return
	 * "worker/worker-registration-form"; }
	 */
	
	@GetMapping("/registerWorker")
	public String showWorkerRegistrationForm(Model theModel) {
		// set initial values in registration form
		Worker worker = new Worker();
		worker.setCostPerUnit(200.0);
		worker.setEducation(new Education());
		worker.setexperienceYears(1.0);
		
		// add worker to model
		theModel.addAttribute("worker", worker);
		return "worker/worker-registration-form";
	}

	
	@PostMapping("/registerWorker")
	public String checkWorkerRegistrationInfo(@Valid Worker worker, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "worker/worker-registration-form";
		}
		if (registerWorker(worker) != null) {
			return "worker/worker-registration-successful";
		} else {
			return "registration-failure";
		}
	}

	private Object registerWorker(@Valid Worker worker) {
		Worker retWorker = workerService.save(worker);
		log.info("----------------User saved : " + retWorker + "---------------");
		return retWorker;
	}
	
	@GetMapping("/logoutWorker")
	public String logoutWorker(HttpServletRequest request) {
		request.getSession().invalidate(); 
		return "redirect:/loginWorker";
	}
	
}
