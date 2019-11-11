package org.dailyhiring.service;

import org.dailyhiring.entity.Degree;
import org.dailyhiring.entity.Diploma;
import org.dailyhiring.entity.Training;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
	public Degree save(Degree employer);
	public Diploma save(Diploma employer);
	public Training save(Training employer);
}
