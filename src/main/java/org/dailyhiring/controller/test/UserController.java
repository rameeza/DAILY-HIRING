package org.dailyhiring.controller.test;

import java.util.Optional;

import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.dao.test.UserRepository;
import org.dailyhiring.entity.test.Job;
import org.dailyhiring.entity.test.User;
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
	
	@GetMapping("/registerUser")
	public String showUserRegistrationForm(User user) {
		return "test/user/user-registration-form";
	}

	@GetMapping("/loginUser")
	public String showUserLoginForm(User user) {
		return "test/user/user-login-form";
	}

	@GetMapping("/userHomePage")
	public String showUserHomePage(Job job) {
		return "test/user/user-home-page";
	}

	@PostMapping("/loginUser")
	public String checkUserLoginInfo(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "test/user/user-login-form";
		}
		Optional<User> optionalUser = userRepository.findById(user.getEmail());
		if (optionalUser.isPresent()) {
			if (optionalUser.get().getPassword().equals(user.getPassword()))
			//return "userHomePage";
			return "redirect:/userHomePage";
		}
		return "test/user/user-login-failure";
	}

	@PostMapping("/registerUser")
	public String checkUserRegistrationInfo(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "test/user/user-registration-form";
		}

		if (registerUser(user) != null) {
			return "test/user/user-registration-successful";
		} else {
			return "registration-failure";
		}
	}

	private @Valid User registerUser(@Valid User user) {
		User retUser = userRepository.save(user);
		log.info("----------------User saved : " + retUser + "---------------");
		return retUser;

	}

	
	@PostMapping("/hireAWorker")	
	public String hireAWorker(@Valid Job job) {
		log.info("----------------Job submitted by user : " + job + "---------------");		
		return "test/user/user-home-page";
		
	}
}