package org.dailyhiring.service;

import org.dailyhiring.dao.UserRepository;
import org.dailyhiring.entity.User;
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
