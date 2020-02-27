package org.dailyhiring.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.entity.Education;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.entity.Worker;
import org.dailyhiring.service.EmployerServiceImplUsingJena;
import org.dailyhiring.service.JobOfferService;
import org.dailyhiring.service.JobOfferServiceImplUsingJena;
import org.dailyhiring.service.WorkerService;
import org.dailyhiring.service.WorkerServiceImplUsingJena;
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

		WorkerServiceImplUsingJena wsj = new WorkerServiceImplUsingJena();

		if (wsj.findById(worker.getEmail(), request) != null) {
			request.getSession().setAttribute("workerEmail", worker.getEmail());
			
			request.getSession().setMaxInactiveInterval(300); // In seconds

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
		
		String theWorkerEmail = request.getSession().getAttribute("workerEmail").toString();
		JobOfferServiceImplUsingJena jOSImplUsingJena = new JobOfferServiceImplUsingJena();
		List<JobOffer> theJobOffers = jOSImplUsingJena.findAllJobsAWorkerHasAppliedIn(
				theWorkerEmail, request);
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
		
		// add worker to model
		theModel.addAttribute("worker", worker);
		return "worker/worker-registration-form";
	}

	
	@PostMapping("/registerWorker")
	public String checkWorkerRegistrationInfo(@Valid Worker worker, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "worker/worker-registration-form";
		}
		
		WorkerServiceImplUsingJena esj = new WorkerServiceImplUsingJena();
		if (esj.save(worker) != null) {
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
