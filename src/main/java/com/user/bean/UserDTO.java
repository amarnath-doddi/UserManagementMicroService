package com.user.bean;

import java.time.LocalDate;
import java.util.Objects;

public class UserDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate lastUpdated;
	private String phone;
	private String loginId;
	private String password;
	
	public UserDTO() {
	}
	public UserDTO(Long id, String firstName, String lastName, LocalDate lastUpdated, String phone, String loginId,
			String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastUpdated = lastUpdated;
		this.phone = phone;
		this.loginId = loginId;
		this.password = password;
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
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO user = (UserDTO) o;
        return id == user.id 
        		&& Objects.equals(lastName, user.lastName)
        		&& Objects.equals(firstName, user.firstName)
        		&& Objects.equals(loginId, user.loginId)
        		&& Objects.equals(password, user.password)
        		&& Objects.equals(lastUpdated, user.lastUpdated)
        		&& Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, loginId,password, phone, lastUpdated);
    }

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
