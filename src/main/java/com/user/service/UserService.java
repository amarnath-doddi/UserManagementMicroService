package com.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.user.entity.User;

public interface UserService {
	public User createUser(User user);
	public Boolean deleteUser(Long id);
	public User updateUser(User user);
	public User getUser(Long id);
	public List<User> getUsers();
	public Page<User> findAll(Pageable pageable);
	public List<User> getUsersSortBy(Sort by);
	public User findByEmail(String email);
	public List<User> findDistinctByLastNameAndFirstName(String lastName, String firstName);
	public List<User> findByAgeGreaterThanEqual(Integer age);
	public List<User> findByAgeLessThan(Integer age);
	public List<User> findByFirstnameLike(String firstName);
	public List<User> findByFirstnameStartingWith(String firstName);
	public List<User> findByAgeOrderByLastnameDesc(Integer age);
	public List<User> findByFirstnameEndingWith(String firstName);
}
