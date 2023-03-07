package com.exam.examserver.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.examserver.helper.UserFoundException;
import com.exam.examserver.model.User;
import com.exam.examserver.model.UserRole;
import com.exam.examserver.repo.RoleRepository;
import com.exam.examserver.repo.UserRepository;
import com.exam.examserver.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	//Creating User
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		// TODO Auto-generated method stub
		
		User local = this.userRepository.findByUsername(user.getUsername());
		//check if user is already present
		if(local!=null) {
			System.out.println("opps!!");
			throw new UserFoundException("user is already present!!");
		}
		else {
			//create user
			for(UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			local = this.userRepository.save(user);	
		}
		return local;
	}


	//getting by Username
	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return this.userRepository.findByUsername(username);
	}


	@Override
	public List<User> getAllDetails() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	//delete by Id
	@Override
	public void  deleteUser(Long userId) {
		// TODO Auto-generated method stub
		this.userRepository.deleteById(userId);
	}


	@Override
	public User updateUser(User updatedUser,Long userId) {
		// TODO Auto-generated method stub
		if(this.userRepository.existsById(updatedUser.getid())) {
			this.userRepository.save(updatedUser);
		}else {
			System.out.println("User does not exists");
		}
		return updatedUser;
	}

}
