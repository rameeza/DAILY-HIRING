package org.dailyhiring.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dailyhiring.Application;
import org.dailyhiring.dao.DBFileRepository;
import org.dailyhiring.dao.EmployerRepository;
import org.dailyhiring.entity.DBFile;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.JobOffer;
import org.dailyhiring.service.EmployerService;
import org.dailyhiring.service.EmployerServiceImplUsingJena;
import org.dailyhiring.service.JobOfferService;
import org.dailyhiring.service.JobOfferServiceImplUsingJena;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.dailyhiring.entity.Employer;

@Controller
public class EmployerController {
	@Autowired
	private JobOfferService jobOfferService;

	@Autowired
	private DBFileRepository dBFileRepository;

	
	@Autowired
	private JobOfferService jobOfferServiceForJena;

	@Autowired
	@Qualifier("empServiceImpl")
	private EmployerService employerService;

	@Autowired
	@Qualifier("empServiceImplUsingJena")
	private EmployerService employerServiceForJena;

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	/*
	 * @Autowired public EmployerController(EmployerService employerService) {
	 * this.employerService = employerService; }
	 * 
	 */
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws ServletException {

	// Convert multipart object to byte[]
	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

}
	
	@GetMapping("/loginEmployer")
	public String showEmployerLoginForm(Employer employer) {
		return "employer/employer-login-form";
	}

	@GetMapping("/employerHomePage")
	public String showEmployerHomePage(Employer employer) {
		return "employer/employer-home-page";
	}

	@GetMapping("/employerProfilePage")
	public String showEmployerProfilePage() {
		return "employer/employer-profile-page";
	}

	@GetMapping("/editEmployerProfile")
	public String editEmployerProfile(Employer employer, HttpServletRequest request, Model theModel) {
		employer = (Employer) request.getSession().getAttribute("employer");
		// System.out.println("-------------employer.getEmail() is = " +
		// employer.getEmail());
		// System.out.println("-------------employer.getDateOfBirth() is = " +
		// employer.getDateOfBirth());
		// System.out.println("-------------employer.getPassword() is = " +
		// employer.getPassword());
		theModel.addAttribute("employer", employer);
		return "employer/edit-employerProfile-form";
	}

	@PostMapping("/editEmployerProfile")
	public String editEmployerProfile(@Valid Employer employer, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "employer/edit-employerProfile-form";
		}
		employer.setEmail(((Employer) request.getSession().getAttribute("employer")).getEmail());
		EmployerServiceImplUsingJena esj = new EmployerServiceImplUsingJena();
		if (esj.editProfile(employer, request) != null) {
			return "employer/employer-profile-page";
		} else {
			return "employer/employer-edit-profile-failure";
		}
	}

	@GetMapping("/employerPostedJobsPage")
	public String showEmployerPostedJobsPage(HttpServletRequest request, Model theModel) {

		// List<JobOffer> theJobOffers = jobOfferService
		// .findAllJobsPostedBy(((Employer)
		// request.getSession().getAttribute("employer")).getId());

		Employer employer = (Employer) request.getSession().getAttribute("employer");
		JobOfferServiceImplUsingJena jOSImplUsingJena = new JobOfferServiceImplUsingJena();
		List<JobOffer> theJobOffers = jOSImplUsingJena.findAllJobsPostedBy(employer, request);

		// add jobs to the spring model
		theModel.addAttribute("jobs", theJobOffers);
		return "employer/employer-posted-jobs-page";
	}

	@GetMapping("/employerAboutPage")
	public String showEmployerAboutPage() {
		return "employer/employer-about-page";
	}

	/*
	 * @PostMapping("/loginEmployer") public String checkEmployerLoginInfo(@Valid
	 * Employer employer, BindingResult bindingResult, HttpServletRequest request) {
	 * if (bindingResult.hasErrors()) {
	 * System.out.println("--------------->"+getClass() +
	 * "; inside if (bindingResult.hasErrors())");
	 * System.out.println("--------------->"+bindingResult.getErrorCount());
	 * System.out.println("--------------->"+bindingResult.toString()); return
	 * "employer/employer-login-form"; } Employer tempEmployer= employerService.
	 * findById(employer.getId()); if (tempEmployer == null) { return
	 * "employer/employer-login-failure"; } if
	 * (tempEmployer.getPassword().equals(employer.getPassword())) { // start
	 * session and add email to it
	 * request.getSession().setAttribute("employerEmail", tempEmployer.getEmail());
	 * 
	 * request.getSession().setAttribute("employerId", tempEmployer.getId());
	 * request.getSession().setAttribute("employer", tempEmployer);
	 * request.getSession().setMaxInactiveInterval(300); // In seconds
	 * 
	 * System.out.println("-------------" + getClass() + ";  " +
	 * "employer.getEmail() = " + tempEmployer.getEmail());
	 * 
	 * 
	 * PRG(post/redirect/get) pattern -> redirect to avoid multiple form submissions
	 * on page refresh
	 * 
	 * return "redirect:/employer-home-page"; } return
	 * "employer/employer-login-failure"; }
	 */

	@PostMapping("/loginEmployer")
	public String checkEmployerLoginInfo(@Valid Employer employer, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			System.out.println("--------------->" + getClass() + "; inside if (bindingResult.hasErrors())");
			System.out.println("--------------->" + bindingResult.getErrorCount());
			System.out.println("--------------->" + bindingResult.toString());
			return "employer/employer-login-form";
		}

		EmployerServiceImplUsingJena esj = new EmployerServiceImplUsingJena();

		if (esj.findById(employer.getEmail(), request) != null) {

			DBFile profilePic = dBFileRepository.findByEmail(employer.getEmail());
			String path = profilePic.getPath();
			path = path.substring(path.lastIndexOf("\\")+1);
			path="profilepics/"+path;
			System.out.println("RZ >>>>>>>>>>>>>> profilePic.getPath() = " + path);
			request.getSession().setAttribute("employerProfilePicPath", path);

			request.getSession().setAttribute("employerEmail", employer.getEmail());
			request.getSession().setAttribute("employerId", employer.getEmail());
			System.out.println(">>>>>>>>>>>>> Before adding employer to session | Employee name:" + employer.getName());

			// request.getSession().setAttribute("employer", employer);
			request.getSession().setMaxInactiveInterval(300); // In seconds

			System.out
					.println("------------->>>>" + getClass() + ";  " + "employer.getEmail() = " + employer.getEmail());
			return "employer/employer-home-page";

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
	public String checkEmployerRegistrationInfo(@Valid Employer employer, 
			HttpServletRequest request,
			BindingResult bindingResult) {
		System.out.println("\t\t>>>>>>>>>>>>Latitude of Employer got from form : " + employer.getLatitude());

		
		
		if (bindingResult.hasErrors()) {
			return "employer/employer-registration-form";
		}
		DBFile profilePic = employer.getProfilepic();
		
		/*
		String filePath = request.getServletContext().getRealPath("/");
		*/ 
		
		try {
			String fileNameExtension = profilePic.getData().getOriginalFilename().substring(
					profilePic.getData().getOriginalFilename().lastIndexOf("."));
			String path = "C:\\Eclipse-Workspaces\\WorkspaceForNITproject\\DAILY-HIRING\\src\\"
					+ "main\\resources\\static\\profilepics\\"+
					employer.getEmail().
					replaceAll("\\.", "_").
					replaceAll("@", "at")+ fileNameExtension;
			
			profilePic.getData().transferTo(new File(path));
			profilePic.setEmail(employer.getEmail());
			profilePic.setPath(path);
			dBFileRepository.save(profilePic);
			System.out.println("RZ>>>>>>>>>>>>>>>>>>>>>>>> File uploaded successfully");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		/*
		System.out.println("RZ>>>>>>>>>>>>>>>>>>>>>>>> .getContentType()"+profilePic.getData().getContentType());
		System.out.println("RZ>>>>>>>>>>>>>>>>>>>>>>>> .getSize()"+profilePic.getData().getSize());
		System.out.println("RZ>>>>>>>>>>>>>>>>>>>>>>>> .getName()"+profilePic.getData().getName());
		System.out.println("RZ>>>>>>>>>>>>>>>>>>>>>>>> .getOriginalFilename()"+profilePic.getData().getOriginalFilename());
		*/
		
		EmployerServiceImplUsingJena esj = new EmployerServiceImplUsingJena();
		if (esj.save(employer) != null) {
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
