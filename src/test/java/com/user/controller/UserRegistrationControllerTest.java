package com.user.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.user.bean.UserDTO;
import com.user.exception.DuplicateEntryException;
import com.user.exception.UserNotfoundException;
import com.user.service.UserRegistrationServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class UserRegistrationControllerTest {
	@Mock
	private UserRegistrationServiceImpl registrationServiceImpl;
	@InjectMocks
	private UserRegistrationController userRegistrationController;
	
	private static List<UserDTO> users;
	private static UserDTO user;
	private static UserDTO user1;
	@BeforeAll
	public static void setUp() {
		LocalDate date = LocalDate.now();
		LocalDate date1 = LocalDate.now();
		user = new UserDTO();
		user.setFirstName("Amar");
		user.setLastName("Doddi");
		user.setPhone("987654321");
		user.setLoginId("amarnath@gmail.com");
		user.setPassword("test");
		user.setLastUpdated(date);
		
		user1 = new UserDTO();
		user1.setFirstName("Amar1");
		user1.setLastName("Doddi1");
		user1.setPhone("9876543211");
		user1.setLoginId("amarnath@gmail.com");
		user1.setPassword("test1");
		user1.setLastUpdated(date1);
		
		users = new ArrayList<>();
		users.add(user);
		users.add(user1);
		
		
	}
	
	@Test
	@Order(1)
	@DisplayName("Get all users test")
	void testGetAllUsers() {
		when(registrationServiceImpl.getUsers()).thenReturn(users);
		
		List<UserDTO> persistedUsers = userRegistrationController.getUsers().getBody();
		
		verify(registrationServiceImpl).getUsers();
		
		assertEquals(users, persistedUsers);
	}
	
	@Test
	@Order(2)
	@DisplayName("Negitive Senario: Get all users test")
	void testNoUsers() {
		when(registrationServiceImpl.getUsers()).thenReturn(null);
		
		List<UserDTO> persistedUsers = userRegistrationController.getUsers().getBody();
		
		assertNull(persistedUsers);
	}
	
	@Test
	@Order(3)
	@DisplayName("Get user by id test")
	void testGetUserById() {
		when(registrationServiceImpl.getUser(any(Long.class))).thenReturn(user);
		
		UserDTO persistedUser = userRegistrationController.getUser(1L).getBody();
		
		verify(registrationServiceImpl).getUser(1L);
		
		assertEquals(user, persistedUser);
	}
	
	@Test
	@Order(4)
	@DisplayName("No user exist with id")
	void testGetNoUserById() {
		when(registrationServiceImpl.getUser(any(Long.class))).thenReturn(null);
		
		assertThrows(UserNotfoundException.class, ()->userRegistrationController.getUser(1L));
	}
	
	@Test
	@Order(5)
	@DisplayName("Get User by id: Negative Scenario")
	void testGetUserByIdNotFound() {
		//context
		when(registrationServiceImpl.getUser(5L)).thenThrow(UserNotfoundException.class);
		
		//event
		//outcome
		assertThrows(UserNotfoundException.class, ()->userRegistrationController.getUser(5L));
	}
	
	@Test
	@Order(6)
	@DisplayName("Update user test")
	void testUpdateUser() {
		when(registrationServiceImpl.updateUser(any(UserDTO.class))).thenAnswer(i -> {
			user.setId(1000L);
			UserDTO user = i.getArgument(0);
			user.setId(1000L);
			user.setFirstName("Test");
			return user;
		});
		
		UserDTO persistedUser = userRegistrationController.updateUser(user).getBody();
		
		assertEquals("Test", persistedUser.getFirstName());
	}
	
	@Test
	@Order(7)
	@DisplayName("Unsuccessfull Update user test")
	void testUnsuccessfullUpdateUser() {
		when(registrationServiceImpl.updateUser(any(UserDTO.class))).thenAnswer(i -> {
			UserDTO user = i.getArgument(0);
			user.setId(1000L);
			user.setFirstName("Test");
			return user;
		});
		
		UserDTO persistedUser = userRegistrationController.updateUser(user1).getBody();
		assertNotEquals(user, persistedUser);
	}
	
	@Test
	@Order(8)
	@DisplayName("Delete User Test")
	void testDeleteUser() {
		when(registrationServiceImpl.deleteUser(1L)).thenReturn(true);

		boolean isDeleted = userRegistrationController.deleteUser(1L).getBody();

	    verify(registrationServiceImpl, times(1)).deleteUser(1L);
	    
	    assertTrue(isDeleted);
	}
	
	@Test
	@Order(9)
	@DisplayName("Error deleting userDelete User Test")
	void testErrorDeletingUser() {
		when(registrationServiceImpl.deleteUser(1L)).thenReturn(false);

		boolean isDeleted = userRegistrationController.deleteUser(1L).getBody();

	    verify(registrationServiceImpl, times(1)).deleteUser(1L);

	    assertFalse(isDeleted);
	}
	
	@Test
	@Order(10)
	@DisplayName("Save user test")
	void testSaveUser() {
		when(registrationServiceImpl.createUser(any(UserDTO.class))).thenAnswer(i -> {
			UserDTO user = i.getArgument(0);
			user.setId(1L);
			return user;
		});
		
		UserDTO persistedUser = userRegistrationController.createUser(user).getBody();
		
		assertEquals(user, persistedUser);
	}
	@Test
	@Order(11)
	void testSaveDuplicateEntry() {
		when(registrationServiceImpl.findByLoginId(any(String.class))).thenAnswer(i -> {
			String loginId = i.getArgument(0);
			user.setLoginId(loginId);
			return user;
		});
		UserDTO userByEmail = userRegistrationController.getUserByLoginId("amarnath.doddi1@hcl.com").getBody();
		assertNotNull(userByEmail);
		assertNotEquals("amarnath.doddi@hcl.com", user.getLoginId());
	}
	@Test
	@Order(12)
	@DisplayName("Negitive Senario:Save User with existing loginid")
	void testSaveDuplicateEntry1(){
		user.setLoginId("amarnath.doddi@hcl.com");
		when(registrationServiceImpl.createUser(user)).thenThrow(DuplicateEntryException.class);
		
		assertThrows(DuplicateEntryException.class, ()->userRegistrationController.createUser(user));
	}
	@Test
	@Order(13)
	@DisplayName("Negitive Senario:Save User with firstname lessthan 2")
	void testCreateUserFirstNameLessthan2(){
		user.setFirstName("D");
		when(registrationServiceImpl.createUser(user)).thenThrow(ConstraintViolationException.class);
		
		assertThrows(ConstraintViolationException.class, ()->registrationServiceImpl.createUser(user));
	}
	
	@Test
	@Order(14)
	@DisplayName("Negitive Senario:Save User with lastname lessthan 2")
	void testCreateUserLastNameLessthan2(){
		user.setLastName("D");
		when(registrationServiceImpl.createUser(user)).thenThrow(ConstraintViolationException.class);
		
		assertThrows(ConstraintViolationException.class, ()->registrationServiceImpl.createUser(user));
	}
	
	@Test
	@Order(15)
	@DisplayName("Negitive Senario:Save User with invalid loginid ")
	void testCreateUserWithInvalidEmail(){
		user.setLoginId("test");
		when(registrationServiceImpl.createUser(user)).thenThrow(ConstraintViolationException.class);
		
		assertThrows(ConstraintViolationException.class, ()->registrationServiceImpl.createUser(user));
	}
	
}
