package com.exam.examserver;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.exam.examserver.helper.UserFoundException;
import com.exam.examserver.model.Role;
import com.exam.examserver.model.User;
import com.exam.examserver.model.UserRole;
import com.exam.examserver.service.UserService;

@SpringBootApplication
@EnableAutoConfiguration
public class ExamserverApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ExamserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Starting Application");
		
				
		try {
			User user = new User();
			user.setFname("Abhishek");
			user.setLname("Pandey");
			user.setUsername("Abhishek@23");
			user.setPassword(this.bCryptPasswordEncoder.encode("abcd"));
			user.setEmail("abhishek@test.com");
			user.setAddress("Nagpur");
			user.setMobile(123456L);
			user.setDob("23-10-1996");
			Role role1 = new Role();
			role1.setRoleId(101L);
			role1.setRoleName("Admin");
			
			Set<UserRole> userRoleSet = new HashSet<>();
			UserRole userRole = new UserRole();
			userRole.setRole(role1);
			userRole.setUser(user);
			userRoleSet.add(userRole);
			
			User user1 = this.userService.createUser(user, userRoleSet);
			System.out.println("UserName : "+user1.getUsername());
		} catch (UserFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
//		
	}
//	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET","POST","PUT","DELETE");
			}
		};
	}


}
