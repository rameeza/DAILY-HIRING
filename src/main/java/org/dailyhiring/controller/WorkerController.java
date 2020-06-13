package org.dailyhiring.controller;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.dao.DBFileRepository;
import org.dailyhiring.entity.DBFile;
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
	private DBFileRepository dBFileRepository;


	@Autowired
	private WorkerService workerService;
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	/*
	 * @Autowired public WorkerController(WorkerService workerService) {
	 * this.workerService = workerService; }
	 * 
	 */	

	@GetMapping("/editWorkerProfile")
	public String editEmployerProfile(Worker worker, HttpServletRequest request,
			Model theModel) {
		worker = (Worker)request.getSession().getAttribute("worker");
		theModel.addAttribute("worker", worker);
		return "worker/edit-workerProfile-form";
	}
	
	@PostMapping("/editWorkerProfile")
	public String editWorkerProfile(@Valid Worker worker, 
			BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "worker/edit-workerProfile-form";
		}
		worker.setEmail(((Worker)request.getSession().getAttribute("worker")).getEmail());

		WorkerServiceImplUsingJena wsj = new WorkerServiceImplUsingJena();
		if (wsj.editProfile(worker, request) != null) {
			return "worker/worker-profile-page";
		} else {
			return "worker/worker-edit-profile-failure";
		}
	}

	
	
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
			
			DBFile profilePic = dBFileRepository.findByEmail(worker.getEmail());
			String path = profilePic.getPath();
			path = path.substring(path.lastIndexOf("\\")+1);
			path="profilepics/"+path;
			System.out.println("RZ >>>>>>>>>>>>>> profilePic.getPath() = " + path);
			request.getSession().setAttribute("workerProfilePicPath", path);
			
			
			request.getSession().setAttribute("workerEmail", worker.getEmail());
			
			//so that worker can access as employer as well
			request.getSession().setAttribute("employerProfilePicPath", path);
			request.getSession().setAttribute("employerEmail", worker.getEmail());
			request.getSession().setAttribute("employerId", worker.getEmail());
			
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
		
		DBFile profilePic = worker.getProfilepic();
		
		/*
		String filePath = request.getServletContext().getRealPath("/");
		*/ 
		
		try {
			String fileNameExtension = profilePic.getData().getOriginalFilename().substring(
					profilePic.getData().getOriginalFilename().lastIndexOf("."));
			String path = "C:\\Eclipse-Workspaces\\WorkspaceForNITproject\\DAILY-HIRING\\src\\"
					+ "main\\resources\\static\\profilepics\\"+
					worker.getEmail().
					replaceAll("\\.", "_").
					replaceAll("@", "at")+ fileNameExtension;
								
			profilePic.getData().transferTo(new File(path));
			profilePic.setEmail(worker.getEmail());
			profilePic.setPath(path);
			dBFileRepository.save(profilePic);
			System.out.println("RZ>>>>>>>>>>>>>>>>>>>>>>>> File uploaded successfully");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
