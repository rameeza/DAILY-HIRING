package org.dailyhiring.dao.test;

import java.util.List;

import org.dailyhiring.entity.test.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
