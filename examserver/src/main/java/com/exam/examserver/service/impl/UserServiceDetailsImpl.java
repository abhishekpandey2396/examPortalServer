package com.exam.examserver.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exam.examserver.model.User;
import com.exam.examserver.repo.UserRepository;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userRepository.findByUsername(username);
		if(user==null) {
			System.out.println("user not found"); 
			throw new UsernameNotFoundException("No User Found !!");
		}
		
		return user;
	}

}
