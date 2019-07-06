package org.dailyhiring.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.dao.UserRepository;
import org.dailyhiring.entity.Job;
import org.dailyhiring.entity.User;
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
public class UserController implements WebMvcConfigurer {
	@Autowired
	private UserRepository userRepository;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
		registry.addViewController("/userHomePage").setViewName("userHomePage");
	}
	
	@GetMapping("/RegisterUser")
	public String showUserRegistrationForm(User user) {
		return "user/userRegistrationForm";
	}

	@GetMapping("/LoginUser")
	public String showUserLoginForm(User user) {
		return "user/userLoginForm";
	}

	@GetMapping("/userHomePage")
	public String showUserHomePage(Job job) {
		return "user/userHomePage";
	}

	@PostMapping("/LoginUser")
	public String checkUserLoginInfo(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "user/userLoginForm";
		}
		Optional<User> optionalUser = userRepository.findById(user.getEmail());
		if (optionalUser.isPresent()) {
			if (optionalUser.get().getPassword().equals(user.getPassword()))
			//return "userHomePage";
			return "redirect:/userHomePage";
		}
		return "user/userLoginFailure";
	}

	@PostMapping("/RegisterUser")
	public String checkUserRegistrationInfo(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "user/userRegistrationForm";
		}

		if (registerUser(user) != null) {
			return "user/userRegistrationSuccessful";
		} else {
			return "registrationFailure";
		}
	}

	private @Valid User registerUser(@Valid User user) {
		User retUser = userRepository.save(user);
		log.info("----------------User saved : " + retUser + "---------------");
		return retUser;

	}

	
	@PostMapping("/HireAWorker")	
	public String HireAWorker(@Valid Job job) {
		log.info("----------------Job submitted by user : " + job + "---------------");		
		return "user/userHomePage";
		
	}
}