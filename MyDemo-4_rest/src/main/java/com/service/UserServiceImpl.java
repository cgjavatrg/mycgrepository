package com.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDAO;
import com.data.Account;
import com.data.User;
import com.data.UserDTO;
import com.exception.AccountNotFoundException;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;
	
	@Override
	public UserDTO validateLogin(String username, String password) {
		User u=userDAO.login(username, password);
		if(u==null) {
			throw new AccountNotFoundException("Invalid credential");
		}
		else {
			UserDTO ob=new UserDTO();
			ob.setUsername(u.getUsername());
			ob.setRole(u.getRole());
			ob.setCreatedAt(u.getCreatedAt());
			return ob;
		}
	}
	
	public User createUser(User user) {
		System.out.println(user);
		
		User u1=this.findUserByname(user.getUsername());
		if(u1!=null ) {
			throw new AccountNotFoundException("Duplicate User");
		}
		else {
		
		User u=userDAO.save(user);
		if(u==null) {
			throw new AccountNotFoundException("Records is not inserted in the database");
		}
		else {
			
			return u;
		}
		}
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userDAO.findAll();
	}

	
	
	@Override
	public User findUserByname(String username) {
		// TODO Auto-generated method stub
		Optional<User> uop=userDAO.findById(username);
		if(uop.isPresent()) {
			return uop.get();
		}
		return null;
	}

	@Override
	public Set<Account> getAccountsByUsername(String username) {
		// TODO Auto-generated method stub
		User user=this.findUserByname(username);
		return user.getAccounts();
	}

}
