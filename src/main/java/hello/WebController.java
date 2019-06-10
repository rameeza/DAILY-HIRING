package hello;

import java.util.Optional;

import javax.validation.Valid;

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
public class WebController implements WebMvcConfigurer {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WorkerRepository workerRepository;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
		registry.addViewController("/userHomePage").setViewName("userHomePage");
	}
	
	@GetMapping("/RegisterUser")
	public String showUserRegistrationForm(User user) {
		return "userRegistrationForm";
	}

	@GetMapping("/LoginUser")
	public String showUserLoginForm(User user) {
		return "userLoginForm";
	}

	@GetMapping("/LoginWorker")
	public String showWorkerLoginForm(Worker worker) {
		return "workerLoginForm";
	}
	
	@GetMapping("/userHomePage")
	public String showUserHomePage(Job job) {
		return "userHomePage";
	}

	@PostMapping("/LoginUser")
	public String checkUserLoginInfo(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "userLoginForm";
		}
		Optional<User> optionalUser = userRepository.findById(user.getEmail());
		if (optionalUser.isPresent()) {
			if (optionalUser.get().getPassword().equals(user.getPassword()))
			//return "userHomePage";
			return "redirect:/userHomePage";
		}
		return "userLoginFailure";
	}

	@PostMapping("/LoginWorker")
	public String checkWorkerLoginInfo(@Valid Worker worker, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "workerLoginForm";
		}
		Optional<Worker> optionalWorker = workerRepository.findById(worker.getEmail());
		if (optionalWorker.isPresent()) {
			if (optionalWorker.get().getPassword().equals(worker.getPassword()))
			return "workerHomePage";
		}
		return "workerLoginFailure";
	}

	@PostMapping("/RegisterUser")
	public String checkUserRegistrationInfo(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "userRegistrationForm";
		}

		if (registerUser(user) != null) {
			return "userRegistrationSuccessful";
		} else {
			return "registrationFailure";
		}
	}

	private @Valid User registerUser(@Valid User user) {
		User retUser = userRepository.save(user);
		log.info("----------------User saved : " + retUser + "---------------");
		return retUser;

	}

	@GetMapping("/RegisterWorker")
	public String showWorkerRegistrationForm(Worker worker) {
		return "workerRegistrationForm";
	}

	@PostMapping("/RegisterWorker")
	public String checkWorkerRegistrationInfo(@Valid Worker worker, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "workerRegistrationForm";
		}
		// fetch all users
		/*
		 * for (User tempUser : userRepository.findAll()) {
		 * log.info(tempUser.toString()); }
		 */
		if (registerWorker(worker) != null) {
			return "workerRegistrationSuccessful";
		} else {
			return "registrationFailure";
		}
	}

	private Object registerWorker(@Valid Worker worker) {
		Worker retWorker = workerRepository.save(worker);
		log.info("----------------User saved : " + retWorker + "---------------");
		return retWorker;
	}
	
	@PostMapping("/HireAWorker")	
	public String HireAWorker(@Valid Job job) {
		log.info("----------------Job submitted by user : " + job + "---------------");		
		return "userHomePage";
		
	}
}