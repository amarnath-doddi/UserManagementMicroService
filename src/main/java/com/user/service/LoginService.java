package com.user.service;

public interface LoginService {
	public boolean login(String userId, String password);
	public boolean resetPassword(String userId, String password);
	public boolean resetUserId(String userId, String newUserId);
}
