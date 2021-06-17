package com.user.service;

import java.util.List;

import com.user.bean.UserDTO;

public interface UserRegistrationService {
	public UserDTO createUser(UserDTO user);
	public Boolean deleteUser(Long id);
	public UserDTO updateUser(UserDTO user);
	public UserDTO getUser(Long id);
	public List<UserDTO> getUsers();
	public UserDTO findByLoginId(String userId);
}
