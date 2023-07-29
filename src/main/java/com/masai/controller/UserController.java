package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.User;
import com.masai.exception.UserException;
import com.masai.service.UserServicesImple;

@RestController
public class UserController {

	@Autowired
	private UserServicesImple uservices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public ResponseEntity<User> saveUserController(@RequestBody User user) throws UserException {
		System.out.println(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		User saveuser = uservices.saveUser(user);

		return new ResponseEntity<User>(saveuser, HttpStatus.CREATED);
	}

	@PostMapping("/adminsignup/{pass}")
	public ResponseEntity<User> saveAdminController(@RequestBody User user, @PathVariable("pass") int pass)
			throws UserException {
		System.out.println(user + " " + pass);
		if (pass != 12345)
			throw new UserException("Wrong Enter Path varable to save User role as ADMIN");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_ADMIN");
		User saveuser = uservices.saveUser(user);

		return new ResponseEntity<User>(saveuser, HttpStatus.ACCEPTED);
	}

	@GetMapping("/forAll")
	public ResponseEntity<String> helloforAll() {
		return new ResponseEntity<String>("hello for All ", HttpStatus.ACCEPTED);
	}

	@GetMapping("/forAdmin")
	public ResponseEntity<String> helloforAdmin() {
		return new ResponseEntity<String>("hello for Admin ", HttpStatus.ACCEPTED);
	}

	@GetMapping("/forAdminAndUser")
	public ResponseEntity<String> helloforAdminAndUser() {
		return new ResponseEntity<String>("hello for Admin And User ", HttpStatus.ACCEPTED);
	}

}