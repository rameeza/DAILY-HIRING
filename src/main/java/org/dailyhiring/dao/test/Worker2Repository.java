package org.dailyhiring.dao.test;

import org.dailyhiring.entity.test.Worker2;
import org.springframework.data.repository.CrudRepository;

public interface Worker2Repository extends CrudRepository<Worker2, String> {
	
	//public Boolean isRegistered(Worker2 worker);

}
