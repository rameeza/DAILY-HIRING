package org.dailyhiring.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.dao.test.Worker2Repository;
import org.dailyhiring.entity.test.Worker2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class Worker2Controller implements WebMvcConfigurer {
	@Autowired
	private Worker2Repository workerRepository;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
		registry.addViewController("/userHomePage").setViewName("userHomePage");
	}
	
	@GetMapping("/LoginWorker")
	public String showWorkerLoginForm(Worker2 worker) {
		return "worker/workerLoginForm";
	}
	

	@PostMapping("/LoginWorker")
	public String checkWorkerLoginInfo(@Valid Worker2 worker, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "worker/workerLoginForm";
		}
		Optional<Worker2> optionalWorker = workerRepository.findById(worker.getEmail());
		if (optionalWorker.isPresent()) {
			if (optionalWorker.get().getPassword().equals(worker.getPassword()))
			return "worker/workerHomePage";
		}
		return "worker/workerLoginFailure";
	}


	@GetMapping("/RegisterWorker")
	public String showWorkerRegistrationForm(Worker2 worker) {
		return "worker/workerRegistrationForm";
	}

	@PostMapping("/RegisterWorker")
	public String checkWorkerRegistrationInfo(@Valid Worker2 worker, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "worker/workerRegistrationForm";
		}
		// fetch all users
		/*
		 * for (User tempUser : userRepository.findAll()) {
		 * log.info(tempUser.toString()); }
		 */
		if (registerWorker(worker) != null) {
			return "worker/workerRegistrationSuccessful";
		} else {
			return "registrationFailure";
		}
	}

	private Object registerWorker(@Valid Worker2 worker) {
		Worker2 retWorker = workerRepository.save(worker);
		log.info("----------------User saved : " + retWorker + "---------------");
		return retWorker;
	}
	
}