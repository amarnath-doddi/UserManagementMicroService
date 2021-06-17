package com.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

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
import com.user.entity.User;
import com.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class UserRegistrationServiceImplTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserRegistrationServiceImpl userRegistrationServiceImpl;
	
	private static UserDTO user;
	
	private static UserDTO userPersisted;
	
	@BeforeAll
	public static void setUp() {
		LocalDate date = LocalDate.now();
		user = new UserDTO();
		user.setFirstName("Amar");
		user.setLastName("Doddi");
		user.setPhone("987654321");
		user.setLoginId("amarnath@gmail.com");
		user.setPassword("test");
		user.setLastUpdated(date);
		
		userPersisted = new UserDTO();
		userPersisted.setId(1L);
		userPersisted.setFirstName("Amar");
		userPersisted.setLastName("Doddi");
		userPersisted.setPhone("987654321");
		userPersisted.setLoginId("amarnath.doddi@hcl.com");
		userPersisted.setPassword("test");
		userPersisted.setLastUpdated(date);
	}
	
	@Test
	@DisplayName("Save User Test")
	@Order(1)
	void testCreateUer(){
		//context
		when(userRepository.save(any(User.class))).thenAnswer(i -> {
			User user = i.getArgument(0);
			user.setId(1L);
			return user;
		});
		//event
		UserDTO savedUser = userRegistrationServiceImpl.createUser(user);
		//outcome
		assertEquals(savedUser,userPersisted);
	}
	
	@Test
	@DisplayName("Update User Test")
	@Order(2)
	void testUpdateUser() {
		when(userRepository.saveAndFlush(any(User.class))).thenAnswer(i -> {
			User user = i.getArgument(0);
			user.setId(1L);
			user.setLoginId("temploginid");
			return user;
		});
		UserDTO updateUser = userRegistrationServiceImpl.updateUser(user);
		assertEquals("temploginid",updateUser.getLoginId());
	}
	
	@Test
	@Order(3)
	@DisplayName("Delete User Test")
	void testDeleteUser() {
		//when(userRepository.findById(1L)).thenReturn(Optional.of(user.getUser()));

		assertTrue(userRegistrationServiceImpl.deleteUser(1L));

	    //verify(userRepository, times(1)).delete(user.getUser());
	}
	@Test
	@Order(4)
	@DisplayName("Test findByLoginId")
	void testfindByLoginId() {
		when(userRepository.findByLoginId(any(String.class))).thenAnswer(i -> {
			String loginId = i.getArgument(0);
			user.setLoginId(loginId);
			return userRegistrationServiceImpl.getUser(user);
		});
		
		UserDTO dbUser = userRegistrationServiceImpl.findByLoginId("amarnath@gmail.com");
		assertNotNull(dbUser);
		assertEquals("amarnath@gmail.com",userRegistrationServiceImpl.findByLoginId("amarnath@gmail.com").getLoginId());
	}
	
	@Test
	@DisplayName("Test getUsers")
	void testgetUsers() {
		List<UserDTO> userAccount =  userRegistrationServiceImpl.getUsers();
		assertNotNull(userAccount);
	}
	@Test
	@DisplayName("Test getUser")
	void testgetUser() {
		UserDTO userAccount =  userRegistrationServiceImpl.getUser(1000L);
		assertNotNull(userAccount);
	}

}
