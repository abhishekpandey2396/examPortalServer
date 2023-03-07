package com.exam.examserver.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.examserver.model.User;
import com.exam.examserver.model.UserRole;


public interface UserService {

	public User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	public User getUser(String username);
	
	public void deleteUser(Long userId);
	
	public List<User> getAllDetails();
	
	public User updateUser(User updatedUser,Long userId);

	
}
