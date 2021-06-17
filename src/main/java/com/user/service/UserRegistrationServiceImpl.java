package com.user.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.bean.AccountDTO;
import com.user.bean.UserAccountDTO;
import com.user.bean.UserDTO;
import com.user.entity.User;
import com.user.repository.UserRepository;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FundService fundService;
	
	@Override
	@Transactional
	public UserDTO createUser(UserDTO user) {
		User savedUserOpt = userRepository.saveAndFlush(getUser(user));
		UserDTO userDto = getUserDTO(savedUserOpt);
		LocalDate localDate = LocalDate.now();
		UserAccountDTO userAccountDTO = new UserAccountDTO();
		AccountDTO account = new AccountDTO();
		account.setBalance(0.00);
		account.setAccountNumber(localDate.toEpochDay());
		userAccountDTO.setAccountDTO(account);
		userAccountDTO.setUserId(userDto.getId());
		fundService.createAccount(userAccountDTO);
		return userDto;
	}

	@Override
	public Boolean deleteUser(Long id) {
		userRepository.deleteById(id);
		return true;
	}

	@Override
	public UserDTO updateUser(UserDTO user) {
		return getUserDTO(userRepository.saveAndFlush(getUser(user)));
	}

	@Override
	public UserDTO getUser(Long id) {
		return getUserDTO(userRepository.findById(id).orElse(new User()));
	}

	@Override
	public List<UserDTO> getUsers() {
		return userRepository.findAll().stream().map(user -> getUserDTO(user)).collect(Collectors.toList());
	}

	@Override
	public UserDTO findByLoginId(String loginId) {
		return getUserDTO((userRepository.findByLoginId(loginId)));
	}
	
	private UserDTO getUserDTO(User user) {
		user = Optional.ofNullable(user).orElse(new User());
		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getLastUpdated(), user.getPhone(),
				user.getLoginId(),user.getPassword());
	}
	
	public User getUser(UserDTO userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setLastUpdated(userDto.getLastUpdated());
		user.setPhone(userDto.getPhone());
		user.setLoginId(userDto.getLoginId());
		user.setPassword(userDto.getPassword());
		return user;
	}
	
	interface TransformUser{
		default UserDTO getUserDTO(User user) {
			user = Optional.ofNullable(user).orElse(new User());
			return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getLastUpdated(), user.getPhone(),
					user.getLoginId(),user.getPassword());
		}
	}
}
