package org.dailyhiring.dao;

import org.dailyhiring.entity.Employer;
import org.springframework.data.repository.CrudRepository;

public interface EmployerRepository extends CrudRepository<Employer, String> {

}
