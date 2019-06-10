package hello;

import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker, String> {
	
	//public Boolean isRegistered(Worker worker);

}
