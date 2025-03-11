package com.example.account.service;

import java.util.List;

import com.example.account.exception.UserNotFoundException;
import com.example.account.model.User;

public interface UserService {
	
	User addUser(User user);
	List<User> getUsers();
	User findUser(Long id) throws UserNotFoundException;
	User updateUser(User user, Long id) throws UserNotFoundException;	
	void deleteUser(Long id) throws UserNotFoundException;
	
	List<User> getUserProfiles();
}
