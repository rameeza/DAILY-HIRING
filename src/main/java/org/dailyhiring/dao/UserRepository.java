package org.dailyhiring.dao;

import org.dailyhiring.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
