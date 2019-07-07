package org.dailyhiring.service.test;

import org.dailyhiring.dao.test.Worker2Repository;
import org.dailyhiring.entity.test.Worker2;
import org.springframework.beans.factory.annotation.Autowired;

public class Worker2Service {
	@Autowired
	private Worker2Repository workerRepository;

	public Boolean registerWorker(Worker2 worker) {
		System.out.println("I am in registerWorker");
		Worker2 temp = workerRepository.save(worker);
		if (temp != null)
			return true;
		else
			return false;

	}

}
