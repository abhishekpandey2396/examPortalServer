package com.exam.examserver.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examserver.helper.UserFoundException;
import com.exam.examserver.model.Role;
import com.exam.examserver.model.User;
import com.exam.examserver.model.UserRole;
import com.exam.examserver.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userservice; 
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	//Creating user
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception {
		
		user.setPassword(this.bcryptPasswordEncoder.encode(user.getPassword()));
		
		Role role2 = new Role();
		role2.setRoleId(102L);
		role2.setRoleName("Normal");
		
		UserRole ur = new UserRole();
		ur.setRole(role2);
		ur.setUser(user);
		
		Set<UserRole> roles = new HashSet<>();
		roles.add(ur);
		//ResponseEntity<User> re = new ResponseEntity<User>(HttpStatus.OK);
		return this.userservice.createUser(user, roles);
	}
	
	//getAll Details
	@GetMapping("")
	public ResponseEntity<List<User>> getAllDetails() {
		System.out.println("inside Controller getAllDetails : ");
//		this.userservice.getAllDetails();
//    	ResponseEntity<List<User>> re = new ResponseEntity<List<User>>(HttpStatus.OK);
	    return new ResponseEntity<List<User>>(this.userservice.getAllDetails(),HttpStatus.OK);
	}
	
	//Finding by username
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return this.userservice.getUser(username);
	}
	
	//Delete by Id
	@DeleteMapping("/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
		this.userservice.deleteUser(userId);
		ResponseEntity<User> re = new ResponseEntity<User>(HttpStatus.OK);
		return re;
	}
	
	//Update User
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId,@RequestBody User updateUser) {
		ResponseEntity<User> re = new ResponseEntity<User>(this.userservice.updateUser(updateUser, userId),HttpStatus.OK);;
		return re;
	}
	
	@ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }
}
