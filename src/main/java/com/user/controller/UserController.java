package com.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.exception.DuplicateEntryException;
import com.user.exception.UserNotfoundException;
import com.user.service.UserService;

@RestController
//@RequestMapping("/api/users")
public class UserController {
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public UserService getUserService() {
		return userService;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<User>> getUsers(){
		List<User> users = userService.getUsers();
		if(users==null) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotfoundException{
		User user = userService.getUser(id);
		if(user==null) {
			//return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
			throw new UserNotfoundException("User doesn't exist with id :"+id);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<User> createUser(@RequestBody User user) throws DuplicateEntryException{
		User existingUser = userService.findByEmail(user.getEmail());
		if(existingUser!=null) {
			throw new DuplicateEntryException("User already exist!");
		}
		User createdUser = userService.createUser(user);
		if(createdUser!=null) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(createdUser,HttpStatus.OK);
	} 
	
	@PutMapping("/")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		User udatedUser = userService.updateUser(user);
		if(udatedUser==null || udatedUser.equals(user)) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
		boolean isDeleted = userService.deleteUser(id);
		if(!isDeleted) {
			return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Boolean>(isDeleted,HttpStatus.OK);
	} 
	
	@GetMapping("/page/size")
	public ResponseEntity<Map<String,Object>> getUsersByPagination(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "2") int size){
		try {
			List<User> users = new ArrayList<>();
			Pageable paging = PageRequest.of(page, size);
			Page<User> userPage = userService.findAll(paging);
			users = userPage.getContent();
			Map<String,Object> resp = new HashMap<>();
			resp.put("users",users);
			resp.put("currentPage", userPage.getNumber());
			resp.put("totalItems", userPage.getTotalElements());
			resp.put("totalPages", userPage.getTotalPages());
			return new ResponseEntity<>(resp,HttpStatus.OK);
		
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/sort/{sortby}/order/{orderby}")
	public List<User> getUsersBySort(@PathVariable String sortby,@PathVariable String orderby){
		Sort sort = (orderby.equalsIgnoreCase("asc"))?Sort.by(sortby).ascending():Sort.by(sortby).descending();
		return userService.getUsersSortBy(sort);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws UserNotfoundException{
		User user = userService.findByEmail(email);
		if(user==null) {
			throw new UserNotfoundException("User doesn't exist with email :"+email);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	@GetMapping("/firstName/{firstName}/lastName/{lastName}")
	public List<User> getDistinctUsersByLastNameAndFirstName(@PathVariable String lastName,@PathVariable String firstName){
		return userService.findDistinctByLastNameAndFirstName(lastName, firstName);
	}
	@GetMapping("/ageGreaterThanEqual/{age}")
	public List<User> getUsersByAgeGreaterThanEqual(@PathVariable Integer age){
		return userService.findByAgeGreaterThanEqual(age);
	}
	@GetMapping("/ageLessThan/{age}")
	public List<User> getUsersByAgeLessThan(@PathVariable Integer age){
		return userService.findByAgeLessThan(age);
	}
	@GetMapping("/firstNameLike/{firstName}")
	public List<User> getUsersByFirstNameLike(@PathVariable String firstName){
		return userService.findByFirstnameLike(firstName);
	}
	@GetMapping("/firstNameStartingWith/{firstName}")
	public List<User> getUsersByFirstnameStartingWith(@PathVariable String firstName){
		return userService.findByFirstnameStartingWith(firstName);
	}
	@GetMapping("/byAgeOrderByLastnameDesc/{age}")
	public List<User> getUsersByAgeOrderByLastnameDesc(@PathVariable Integer age){
		return userService.findByAgeOrderByLastnameDesc(age);
	}
	@GetMapping("/firstNameEndingWith/{firstName}")
	public List<User> getUsersByFirstnameEndingWith(@PathVariable String firstName){
		return userService.findByFirstnameEndingWith(firstName);
	}
	
}
