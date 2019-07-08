package org.dailyhiring.service;


import org.dailyhiring.entity.Employer;
import org.springframework.stereotype.Service;

@Service
public interface EmployerService {
	public Employer save(Employer employer);

	public Employer findById(int id);

//	public Employer get(Employer employer);


}
