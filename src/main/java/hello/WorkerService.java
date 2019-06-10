package hello;

import org.springframework.beans.factory.annotation.Autowired;

public class WorkerService {
	@Autowired
	private WorkerRepository workerRepository;

	public Boolean registerWorker(Worker worker) {
		System.out.println("I am in registerWorker");
		Worker temp = workerRepository.save(worker);
		if (temp != null)
			return true;
		else
			return false;

	}

}
