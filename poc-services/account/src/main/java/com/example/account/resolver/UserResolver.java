package com.example.account.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.account.exception.UserNotFoundException;
import com.example.account.model.Profile;
import com.example.account.model.User;
import com.example.account.serviceImpl.ProfileServiceImpl;
import com.example.account.serviceImpl.UserServiceImpl;

@Controller
public class UserResolver {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private ProfileServiceImpl profileService;
	
	@MutationMapping
	public User addUser(@Argument String name, @Argument String email,
			@Argument String bio, @Argument String profilePictureUrl) {
		
		Profile profiles = new Profile();
		profiles.setBio(bio);
		profiles.setProfilePictureUrl(profilePictureUrl);
		
		Profile savedProfile = profileService.addProfile(profiles);
		
		User users = new User();
		users.setName(name);
		users.setEmail(email);
		users.setProfile(savedProfile);
		
		return userService.addUser(users);
	}
	
	@QueryMapping
	public List<User> getUsers() {
		List<User> users = userService.getUsers();
		
		if (users.isEmpty()) {
			throw new UserNotFoundException("No users found!");
		}
		return users;
	}
	
	@QueryMapping
	public User findUser(@Argument Long id) {
		return userService.findUser(id);
	}
	
	@MutationMapping
	public User updateUser(@Argument Long id, @Argument String name, @Argument String email) {
		User updatedUser = new User();
		updatedUser.setName(name);
		updatedUser.setEmail(email);
		return userService.updateUser(updatedUser, id);
	}
	
	@MutationMapping
	public boolean deleteUser(@Argument Long id) {
		userService.deleteUser(id);
		return true;
	}
	
	@QueryMapping
	public List<User> getUserByName(@Argument String name) {
		return userService.getUserByName(name);
	}
	
}
