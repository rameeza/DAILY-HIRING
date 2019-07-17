package org.dailyhiring.controller;


import javax.servlet.http.HttpServletRequest;
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
	@Autowired
	private EmployerService employerService;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	/*
	 * @Autowired public EmployerController(EmployerService employerService) {
	 * this.employerService = employerService; }
	 * 
	 */	
	@GetMapping("/loginEmployer")
	public String showEmployerLoginForm(Employer employer) {
		return "employer/employer-login-form";
	}

	@GetMapping("/employerHomePage")
	public String showEmployerHomePage(Employer employer) {
		return "employer/employer-home-page";
	}
	
	@PostMapping("/loginEmployer")
	public String checkEmployerLoginInfo(@Valid Employer employer, 
				BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			System.out.println("--------------->"+getClass() + "; inside if (bindingResult.hasErrors())");
			System.out.println("--------------->"+bindingResult.getErrorCount());
			System.out.println("--------------->"+bindingResult.toString());
			return "employer/employer-login-form";
		}
		Employer tempEmployer= employerService.
				findById(employer.getId());
		if (tempEmployer == null) {
			return "employer/employer-login-failure";
		}
		if (tempEmployer.getPassword().equals(employer.getPassword())) {
			// start session and add email to it
			request.getSession().setAttribute("employerEmail", tempEmployer.getEmail());
			
			request.getSession().setAttribute("employerId", tempEmployer.getId());
			
			System.out.println("-------------" + getClass() + ";  "
					+ "employer.getEmail() = " +  tempEmployer.getEmail());
			
			/*
			 PRG(post/redirect/get) pattern -> 
				redirect to avoid multiple form submissions on page refresh
			*/	
			return "redirect:/employer-home-page"; 		
		}
		return "employer/employer-login-failure";
	}

	@GetMapping("/employer-home-page")
	public String showHomePage() {
		return "employer/employer-home-page";
		
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
	
	
	@GetMapping("/logoutEmployer")
	public String logoutEmployer(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/loginEmployer";
	}
	
}
