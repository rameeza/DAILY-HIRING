package org.dailyhiring.controller.test;

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
	private Worker2Repository worker2Repository;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
		registry.addViewController("/userHomePage").setViewName("userHomePage");
	}
	
	@GetMapping("/loginWorker2")
	public String showWorker2LoginForm(Worker2 worker2) {
		return "test/worker2/worker2-login-form";
	}
	

	@PostMapping("/loginWorker2")
	public String checkWorker2LoginInfo(@Valid Worker2 worker2, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "test/worker2/worker2-login-form";
		}
		Optional<Worker2> optionalWorker2 = worker2Repository.findById(worker2.getEmail());
		if (optionalWorker2.isPresent()) {
			if (optionalWorker2.get().getPassword().equals(worker2.getPassword()))
			return "test/worker2/worker2-home-page";
		}
		return "test/worker2/worker2-login-failure";
	}

	@GetMapping("/registerWorker2")
	public String showWorker2RegistrationForm(Worker2 worker2) {
		return "test/worker2/worker2-registration-form";
	}

	@PostMapping("/registerWorker2")
	public String checkWorker2RegistrationInfo(@Valid Worker2 worker2, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "test/worker2/worker2-registration-form";
		}
		// fetch all users
		/*
		 * for (User testUser : userRepository.findAll()) {
		 * log.info(testUser.toString()); }
		 */
		if (registerWorker2(worker2) != null) {
			System.out.println("--------------1----------");
			return "test/worker2/worker2-registration-successful";
		} else {
			System.out.println("--------------2----------");
			return "registration-failure";
		}
	}

	private Object registerWorker2(@Valid Worker2 worker2) {
		Worker2 retWorker2 = worker2Repository.save(worker2);
		log.info("----------------Worker saved : " + retWorker2 + "---------------");
		return retWorker2;
	}
	
}