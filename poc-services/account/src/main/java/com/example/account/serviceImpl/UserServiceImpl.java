package com.example.account.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.account.exception.UserNotFoundException;
import com.example.account.model.User;
import com.example.account.repository.UserRepository;
import com.example.account.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public User findUser(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
	}

	@Override
	public User updateUser(User user, Long id) {
		// TODO Auto-generated method stub
		User existingUser = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
		
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		return repository.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		User user = repository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
		repository.delete(user);
	}
	
	public List<User> getUserByName(String name) {
		List<User> users = repository.findByNameContainingIgnoreCase(name);
		if (users.isEmpty()) {
			throw new UserNotFoundException("No users found with name: " + name);
		}
		return users;
	}
	
	public List<User> getUsersByProfileBio(String bio) {
		List<User> users = repository.findByBioContainingIgnoreCase(bio);
		if (users.isEmpty()) {
			throw new UserNotFoundException("No users found with bio: " + bio);
		}
		return users;
	}

	@Override
	public List<User> getUserProfiles() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
