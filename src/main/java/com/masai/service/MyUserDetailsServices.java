package com.masai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masai.entity.User;
import com.masai.model.MyUserDetail;
import com.masai.repository.UserRepository;

@Service
public class MyUserDetailsServices implements UserDetailsService {

	@Autowired
	private UserRepository uDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		Optional<User> user = uDao.findByUsername(username);
		if (user.isPresent()) {
			return new MyUserDetail(user.get());
		}
		throw new UsernameNotFoundException("(My UserDetails Services ) User not present in this username " + username);
	}

}