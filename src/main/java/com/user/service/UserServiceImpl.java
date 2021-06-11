package com.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserRepository getUserRepository() {
		return userRepository;
	}

	@Override
	public User createUser(User user) {
		User savedUser = userRepository.saveAndFlush(user);
		return savedUser;
	}

	@Override
	public Boolean deleteUser(Long id) {
		userRepository.deleteById(id);
		return true;
	}

	@Override
	public User updateUser(User user) {
		User savedUser = null;
		savedUser = userRepository.saveAndFlush(user);
		return savedUser;
	}

	@Override
	public User getUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElse(new User());
	}

	@Override
	public List<User> getUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public List<User> getUsersSortBy(Sort by) {
		return userRepository.findAll(by);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> findDistinctByLastNameAndFirstName(String lastName, String firstName) {
		return userRepository.findDistinctByLastNameAndFirstName(lastName, firstName);
	}

	@Override
	public List<User> findByAgeGreaterThanEqual(Integer age) {
		return userRepository.findByAgeGreaterThanEqual(age);
	}

	@Override
	public List<User> findByAgeLessThan(Integer age) {
		return userRepository.findByAgeLessThan(age);
	}

	@Override
	public List<User> findByFirstnameLike(String firstName) {
		return userRepository.findByFirstNameLike(firstName);
	}

	@Override
	public List<User> findByFirstnameStartingWith(String firstName) {
		return userRepository.findByFirstNameStartingWith(firstName);
	}

	@Override
	public List<User> findByAgeOrderByLastnameDesc(Integer age) {
		return userRepository.findByAgeOrderByLastNameDesc(age);
	}

	@Override
	public List<User> findByFirstnameEndingWith(String firstName) {
		return userRepository.findByFirstNameEndingWith(firstName);
	}

}
