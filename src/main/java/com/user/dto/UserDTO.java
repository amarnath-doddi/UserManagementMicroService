package com.user.dto;

import java.time.LocalDate;

import com.user.entity.User;

public class UserDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate lastUpdated;
	
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
	public UserDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.lastUpdated = user.getLastUpdated();
	}
	public UserDTO(Long id, String firstName, String lastName, LocalDate lastUpdated) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastUpdated = lastUpdated;
	}
	
	public User getUser() {
		return new User(this.id,this.firstName,this.lastName,this.lastUpdated);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
}
