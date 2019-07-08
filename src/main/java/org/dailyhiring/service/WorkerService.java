package org.dailyhiring.service;


import org.dailyhiring.entity.Worker;

public interface WorkerService {
	public Worker save(Worker worker);

	public Worker findById(int id);
}
