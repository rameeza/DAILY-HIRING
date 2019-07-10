package org.dailyhiring.controller;


import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.entity.Education;
import org.dailyhiring.entity.Worker;
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
	public String checkWorkerLoginInfo(@Valid Worker worker, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "worker/worker-login-form";
		}
		Worker tempWorker= workerService.
				findById(worker.getId());
		if (tempWorker == null) {
			return "worker/worker-login-failure";
		}
		if (tempWorker.getPassword().equals(worker.getPassword())) {
			return "worker/worker-home-page";		
		}
		return "worker/worker-login-failure";
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
		worker.setEducation(new Education(0));
		
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
	
}
