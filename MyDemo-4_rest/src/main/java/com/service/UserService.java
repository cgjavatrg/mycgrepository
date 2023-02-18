package com.service;

import java.util.List;
import java.util.Set;

import com.data.Account;
import com.data.User;
import com.data.UserDTO;

public interface UserService {
	public UserDTO validateLogin(String username,String password);
	public User createUser(User user);
	public List<User> getAllUsers();
	public User findUserByname(String username);
	public Set<Account> getAccountsByUsername(String username);
}
