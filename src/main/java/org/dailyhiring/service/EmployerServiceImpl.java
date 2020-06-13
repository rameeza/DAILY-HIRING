package org.dailyhiring.service;

import java.util.Optional;

import org.dailyhiring.dao.EmployerRepository;
import org.dailyhiring.entity.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("empServiceImpl")
public class EmployerServiceImpl implements EmployerService {
	private EmployerRepository employerRepository;

	@Autowired
	public EmployerServiceImpl(EmployerRepository employerRepository) {
		this.employerRepository = employerRepository;
	}


	@Override
	public Employer save(Employer employer) {
		
		Employer tempEmployer = employerRepository.save(employer);
		return tempEmployer;
	}


	@Override
	public Employer findById(int theId) {
		Employer ret = null;
		Optional<Employer> optionalEmployer =  employerRepository.
				findById(theId);
		if (optionalEmployer.isPresent()) {
			ret = optionalEmployer.get();
		}
		return ret;
	}


}
