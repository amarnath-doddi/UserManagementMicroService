package com.user.controller;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.user.bean.UserDTO;
import com.user.exception.InvalidCredentialsException;
import com.user.service.LoginServiceImpl;
import com.user.service.UserRegistrationService;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
	@Mock
	private LoginServiceImpl loginServiceImpl;
	@Mock
	private UserRegistrationService userRegistrationService;
	@InjectMocks
	private LoginController loginController;
	
	private static UserDTO user;
	
	@BeforeAll
	public static void setUp() {
		user = new UserDTO();
		user.setId(1000L);
		user.setLoginId("amarnath@gmail.com");
		user.setPassword("amardoddi");
		user.setFirstName("Amarnath");
		user.setLastName("Doddi");
		user.setPhone("9123456790");
		user.setLastUpdated(LocalDate.now());
		
	}
	
	@Test
	@DisplayName("Login user test")
	void testLogin() {
		when(userRegistrationService.findByLoginId("amarnath@gmail.com")).thenReturn(user);
		//when(loginServiceImpl.login("amardoddi", "amardoddi")).thenReturn(true);
		
		boolean isSuccessfull = loginController.login("amarnath@gmail.com","amardoddi").getBody();
		
		assertTrue(isSuccessfull);
	}
	@Test
	@DisplayName("Negitive Senario:Invalid user name")
	void testLoginInvalidUserName(){
		//when(loginServiceImpl.login("Test", "amardoddi")).thenThrow(InvalidCredentialsException.class);
		
		assertThrows(InvalidCredentialsException.class, ()->loginController.login("Test", "amardoddi"));
	}
	@Test
	@DisplayName("Negitive Senario:Invalid password")
	void testLoginInvalidPassword(){
		//when(loginServiceImpl.login(any(String.class), any(String.class))).thenThrow(InvalidCredentialsException.class);
		
		assertThrows(InvalidCredentialsException.class, ()->loginController.login("amarnath@gmail.com", "Test"));
	}
	
	@Test
	void testValidatePassword() {
		assertThrows(InvalidCredentialsException.class, ()->loginController.validatePassword("amardoddi1", user));
	}
}
