package org.dailyhiring.dao.test;

import org.dailyhiring.entity.test.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
