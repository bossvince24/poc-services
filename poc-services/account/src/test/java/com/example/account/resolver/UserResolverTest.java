package com.example.account.resolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.account.model.Profile;
import com.example.account.model.User;
import com.example.account.serviceImpl.ProfileServiceImpl;
import com.example.account.serviceImpl.UserServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserResolverTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private UserServiceImpl userService;
	
	@MockitoBean
	private ProfileServiceImpl profileService;
	
	@DisplayName("Add User Test")
	@Test
	public void testAddUser() throws Exception {
		
		log.info("Starting Add User Test");
		String graphquery = "{ \"query\": \"mutation { addUser (name: \\\"John Doe\\\", "
                + "email: \\\"john.doe@example.com\\\", bio: \\\"Software Developer\\\", "
                + "profilePictureUrl: \\\"http://example.com/profile.jpg\\\") "
                + "{ name email profile { bio profilePictureUrl } } }\" }";

		
		Profile mockProfile = new Profile();
		mockProfile.setBio("Software Developer");
		mockProfile.setProfilePictureUrl("http://example.com/profile.jpg");
		
		User mockUser = new User();
		mockUser.setName("John Doe");
		mockUser.setEmail("john.doe@example.com");
		mockUser.setProfile(mockProfile);
		
		when(profileService.addProfile(any(Profile.class))).thenReturn(mockProfile);
		when(userService.addUser(any(User.class))).thenReturn(mockUser);
			
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(graphquery))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.addUser.name").value(mockUser.getName()))
		.andExpect(jsonPath("$.data.addUser.email").value(mockUser.getEmail()))
		.andExpect(jsonPath("$.data.addUser.profile.bio").value(mockUser.getProfile().getBio()))
		.andExpect(jsonPath("$.data.addUser.profile.profilePictureUrl").value(mockUser.getProfile().getProfilePictureUrl()));
		
		verify(profileService, times(1)).addProfile(mockProfile);
		verify(userService, times(1)).addUser(mockUser);
		
		log.info("End Add User Test");
	}
	
	@DisplayName("Get User Test")
	@Test
	public void testGetUser() throws Exception {
		
		log.info("Starting Get User Test");
		
		String query = "{ \"query\": \"{ getUsers { id name email } }\" }";
		
		List<User> userslList = Arrays.asList(new User(1L, "John Doe", "john.doe@example.com", null));
			
		when(userService.getUsers()).thenReturn(userslList);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.getUsers[0].name").value(userslList.get(0).getName()))
		.andExpect(jsonPath("$.data.getUsers[0].email").value(userslList.get(0).getEmail()));
		
		verify(userService, times(1)).getUsers();
		
		log.info("End Get User Test");
	}
	
	@DisplayName("Find User Test")
	@Test
	public void testFindUser() throws Exception {
		
		String query = "{ \"query\": \" { findUser (id: 1) { id name email } }\" }";
		
		User mockUser = new User();
		mockUser.setId(1L);
		mockUser.setName("John");
		mockUser.setEmail("john@example.com");
		
		when(userService.findUser(1L)).thenReturn(mockUser);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.findUser.id").value(mockUser.getId()))
		.andExpect(jsonPath("$.data.findUser.name").value(mockUser.getName()))
		.andExpect(jsonPath("$.data.findUser.email").value(mockUser.getEmail()));
		
		verify(userService, times(1)).findUser(1L);
	}
	
	@DisplayName("Get User By Name Test")
	@Test
	public void testGetUserByName() throws Exception {
		
		String query = "{ \"query\": \" { getUserByName (name: \\\"Jane\\\") { name email } }\" }";
		
//		List<User> usersList = Arrays.asList(new User(1L, "Jane", "jane@example.com", null));
		
		User users = new User();
		users.setName("Jane");
		users.setEmail("jane@example.com");
		
		List<User> usersList = Arrays.asList(users);
		
		when(userService.getUserByName("Jane")).thenReturn(usersList);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.getUserByName[0].name").value(usersList.get(0).getName()))
		.andExpect(jsonPath("$.data.getUserByName[0].email").value(usersList.get(0).getEmail()));
		
		verify(userService, times(1)).getUserByName(users.getName());
	}
	
	@DisplayName("Update User Test")
	@Test
	public void testUpdateUser() throws Exception {
		
		
		String query = "{ \"query\": \"mutation { updateUser(id: 1, name: \\\"Jane Doe\\\", email: "
				+ "\\\"jane.doe@example.com\\\") { id name email } }\" }";

		
		User users = new User();
		users.setId(1L);
		users.setName("Jane Doe");
		users.setEmail("jane.doe@example.com");
		
		when(userService.updateUser(any(User.class), eq(1L))).thenReturn(users);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.updateUser.id").value(users.getId()))
		.andExpect(jsonPath("$.data.updateUser.name").value(users.getName()))
		.andExpect(jsonPath("$.data.updateUser.email").value(users.getEmail()));
		
		verify(userService, times(1)).updateUser(any(User.class), eq(users.getId()));
	}
	
	@DisplayName("Delete User Test")
	@Test
	public void testDeleteUser() throws Exception {
		
		String query = "{ \"query\": \"mutation { deleteUser(id: 1) }\" }";
		
		doNothing().when(userService).deleteUser(1L);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.deleteUser").value(true));
		
		verify(userService, times(1)).deleteUser(1L);
		}
	
	@DisplayName("Get Users By Profile Bio")
	@Test
	public void testGetUsersByProfileBio() throws Exception {
		
		String query = "{ \"query\": \" { getUsersByProfileBio (bio: \\\"Software Engineer\\\") { id name email profile { bio profilePictureUrl } } }\" }";
		
		Profile profile = new Profile();
		profile.setBio("Software Engineer");
		profile.setProfilePictureUrl("abc@def.com/jpeg");
		
		User users = new User();
		users.setId(1L);
		users.setName("Joe");
		users.setEmail("joe@example.com");
		users.setProfile(profile);
		
		List<User> userList = Arrays.asList(users);
		
		when(userService.getUsersByProfileBio("Software Engineer")).thenReturn(userList);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.getUsersByProfileBio[0].id").value(userList.get(0).getId()))
		.andExpect(jsonPath("$.data.getUsersByProfileBio[0].name").value(userList.get(0).getName()))
		.andExpect(jsonPath("$.data.getUsersByProfileBio[0].email").value(userList.get(0).getEmail()))
		.andExpect(jsonPath("$.data.getUsersByProfileBio[0].profile.bio").value(userList.get(0).getProfile().getBio()))
		.andExpect(jsonPath("$.data.getUsersByProfileBio[0].profile.profilePictureUrl").value(userList.get(0).getProfile().getProfilePictureUrl()));
		
		verify(userService, times(1)).getUsersByProfileBio(profile.getBio());
	}
	
	@DisplayName("Get User Profile Test")
	@Test
	public void testGetUserProfiles() throws Exception {
		
		String query = "{ \"query\": \"{ getUserProfiles { id name email profile { bio profilePictureUrl } } }\" }";
		
		Profile profiles = new Profile();
		profiles.setBio("Software Engineer");
		profiles.setProfilePictureUrl("abc@def.com/jpg");
		
		User user = new User();
		user.setId(1L);
		user.setName("John Doe");
		user.setEmail("john.doe@example.com");
		user.setProfile(profiles);
		
		List<User> usersProfileList = Arrays.asList(user);
		
		when(userService.getUserProfiles()).thenReturn(usersProfileList);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.getUserProfiles[0].id").value(usersProfileList.get(0).getId()))
		.andExpect(jsonPath("$.data.getUserProfiles[0].profile.bio").value(usersProfileList.get(0).getProfile().getBio()))
		.andExpect(jsonPath("$.data.getUserProfiles[0].profile.profilePictureUrl").value(usersProfileList.get(0).getProfile().getProfilePictureUrl()))
		.andExpect(jsonPath("$.data.getUserProfiles[0].name").value(usersProfileList.get(0).getName()))
		.andExpect(jsonPath("$.data.getUserProfiles[0].email").value(usersProfileList.get(0).getEmail()));
		
		verify(userService, times(1)).getUserProfiles();
	}
}
