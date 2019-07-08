package org.dailyhiring.service.test;

import org.dailyhiring.dao.test.UserRepository;
import org.dailyhiring.entity.test.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
	@Autowired
	private UserRepository userRepository;

	public boolean registerUser(User user) {
		System.out.println("I am in registerUser");
		User temp = userRepository.save(user);
		if (temp != null)
			return true;
		else
			return false;
	}

}