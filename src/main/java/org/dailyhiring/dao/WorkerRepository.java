package org.dailyhiring.dao;

import org.dailyhiring.entity.Worker;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker, String> {
	
	//public Boolean isRegistered(Worker worker);

}
