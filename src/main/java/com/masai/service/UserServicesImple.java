package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entity.User;
import com.masai.exception.UserException;
import com.masai.repository.UserRepository;

@Service
public class UserServicesImple {

	@Autowired
	private UserRepository uDao;

	public User saveUser(User user) throws UserException {
		// TODO Auto-generated method stub
		if (user == null)
			throw new UserException("You can not pass the null value for save the new User");

		if (uDao.findById(user.getId()).isPresent()) {
			throw new UserException("User apready present so we can not Store new User with same Id");
		}

		return uDao.save(user);
	}

}