package org.dailyhiring.service;

import java.util.Optional;

import org.dailyhiring.dao.WorkerRepository;
import org.dailyhiring.entity.Employer;
import org.dailyhiring.entity.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerServiceImpl implements WorkerService {
	private WorkerRepository workerRepository;

	@Autowired
	public WorkerServiceImpl(WorkerRepository workerRepository) {
		this.workerRepository = workerRepository;
	}

	@Override
	public Worker save(Worker worker) {
		Worker tempWorker = workerRepository.save(worker);
		return tempWorker;
	}

	@Override
	public Worker findById(int theId) {
		Worker ret = null;
		Optional<Worker> optionalWorker =  workerRepository.
				findById(theId);
		if (optionalWorker.isPresent()) {
			ret = optionalWorker.get();
		}
		return ret;

	}

}
