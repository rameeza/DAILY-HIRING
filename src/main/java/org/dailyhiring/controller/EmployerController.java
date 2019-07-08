package org.dailyhiring.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.service.EmployerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployerController {
	private EmployerService employerService;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	public EmployerController(EmployerService employerService) {
		this.employerService = employerService;
	}

	@GetMapping("/loginEmployer")
	public String showEmployerLoginForm(Employer employer) {
		return "employer/employer-login-form";
	}

	@PostMapping("/loginEmployer")
	public String checkEmployerLoginInfo(@Valid Employer employer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "employer/employer-login-form";
		}
		System.out.println("-------------------------employer.getId() : " + employer.getId());
		Employer tempEmployer= employerService.
				findById(employer.getId());
		if (tempEmployer == null) {
			return "employer/employer-login-failure";
		}
		if (tempEmployer.getPassword().equals(employer.getPassword())) {
			return "employer/employer-home-page";		
		}
		return "employer/employer-login-failure";
	}

	@GetMapping("/registerEmployer")
	public String showEmployerRegistrationForm(Employer employer) {
		return "employer/employer-registration-form";
	}

	@PostMapping("/registerEmployer")
	public String checkEmployerRegistrationInfo(@Valid Employer employer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "employer/employer-registration-form";
		}
		// fetch all users
		/*
		 * for (User tempUser : userRepository.findAll()) {
		 * log.info(tempUser.toString()); }
		 */
		if (registerEmployer(employer) != null) {
			return "employer/employer-registration-successful";
		} else {
			return "registration-failure";
		}
	}

	private Object registerEmployer(@Valid Employer employer) {
		Employer retEmployer = employerService.save(employer);
		log.info("----------------User saved : " + retEmployer + "---------------");
		return retEmployer;
	}
}
