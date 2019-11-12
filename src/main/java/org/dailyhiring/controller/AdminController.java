package org.dailyhiring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.Worker;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
	
	@GetMapping("/admin")
	public String showEmployerLoginForm(Employer employer) {
		return "admin/admin-home-page";
	}

	@PostMapping("/addACertificate")
	public String addACertificate(HttpServletRequest request) {
		return "admin/admin-home-page";
	}


}
