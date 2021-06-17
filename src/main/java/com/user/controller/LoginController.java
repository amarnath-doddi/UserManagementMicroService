package com.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.bean.UserDTO;
import com.user.exception.InvalidCredentialsException;
import com.user.service.UserRegistrationService;

@RestController
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@GetMapping("/login")
	public ResponseEntity<Boolean> login(@RequestParam String loginId,@RequestParam String password){
		UserDTO user = userRegistrationService.findByLoginId(loginId);
		if(user==null) {
			logger.error("Login id is Incorrect!");
			throw new InvalidCredentialsException("Login id is Incorrect!");
		}
		validatePassword(password, user) ;
		logger.info("User logged in successfull!");
		return new ResponseEntity<>(true,HttpStatus.OK);
	}

	public void validatePassword(String password, UserDTO user){
		if(!user.getPassword().equals(password)) {
			logger.error("Password is Incorrect!");
			throw new InvalidCredentialsException("Password is Incorrect!");
		}
	}
	
	
}
