package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.User;
import com.masai.repository.UserRepository;

@RestController
public class LoginController {

	@Autowired
	private UserRepository uDao;

	@GetMapping("/signin")
	public ResponseEntity<User> getLoggedInCustomerDetailsHandler(Authentication auth) {

		User user = uDao.findByUsername(auth.getName())
				.orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));

		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);

	}
}