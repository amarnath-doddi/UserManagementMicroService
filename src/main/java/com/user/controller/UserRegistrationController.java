package com.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.bean.UserDTO;
import com.user.exception.DuplicateEntryException;
import com.user.exception.UserNotfoundException;
import com.user.service.UserRegistrationService;
@RestController
public class UserRegistrationController {
	Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getUsers(){
		List<UserDTO> users = userRegistrationService.getUsers();
		if(users==null) {
			logger.info("There are no register users exist.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
		UserDTO user = userRegistrationService.getUser(id);
		if(user==null) {
			String message = String.format("User doesn't exist with id :%s", id);
			logger.error(message);
			throw new UserNotfoundException(message);
		}
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){
		UserDTO existingUser = userRegistrationService.findByLoginId(user.getLoginId());
		if(existingUser!=null && existingUser.getLoginId()!=null && !user.getLoginId().equals(existingUser.getLoginId())) {
			logger.error("User already exist!");
			throw new DuplicateEntryException("User already exist!");
		}
		UserDTO createdUser = userRegistrationService.createUser(user);
		if(createdUser==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(createdUser,HttpStatus.OK);
	} 
	
	@PutMapping("/")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user){
		UserDTO udatedUser = userRegistrationService.updateUser(user);
		if(udatedUser==null || !udatedUser.equals(user)) {
			logger.error("Error while updating the user!");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(udatedUser,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
		boolean isDeleted = userRegistrationService.deleteUser(id);
		if(!isDeleted) {
			logger.error("User delete unsuccessfull!");
			return new ResponseEntity<>(isDeleted,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(isDeleted,HttpStatus.OK);
	} 
	
	@GetMapping("/loginId/{loginId}")
	public ResponseEntity<UserDTO> getUserByLoginId(@PathVariable String loginId){
		UserDTO user = userRegistrationService.findByLoginId(loginId);
		if(user==null) {
			String message = String.format("User doesn't exist with loginId :%s", loginId);
			logger.error(message);
			throw new UserNotfoundException(message);
		}
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
}
