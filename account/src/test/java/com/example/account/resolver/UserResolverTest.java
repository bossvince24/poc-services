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
		.andExpect(jsonPath("$.data.addUser.name").value("John Doe"))
		.andExpect(jsonPath("$.data.addUser.email").value("john.doe@example.com"))
		.andExpect(jsonPath("$.data.addUser.profile.bio").value("Software Developer"))
		.andExpect(jsonPath("$.data.addUser.profile.profilePictureUrl").value("http://example.com/profile.jpg"));
		
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
		.andExpect(jsonPath("$.data.findUser.id").value(1))
		.andExpect(jsonPath("$.data.findUser.name").value("John"))
		.andExpect(jsonPath("$.data.findUser.email").value("john@example.com"));
		
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
		.andExpect(jsonPath("$.data.getUserByName[0].name").value("Jane"))
		.andExpect(jsonPath("$.data.getUserByName[0].email").value("jane@example.com"));
		
		verify(userService, times(1)).getUserByName("Jane");
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
		.andExpect(jsonPath("$.data.updateUser.id").value(1L))
		.andExpect(jsonPath("$.data.updateUser.name").value("Jane Doe"))
		.andExpect(jsonPath("$.data.updateUser.email").value("jane.doe@example.com"));
		
		verify(userService, times(1)).updateUser(any(User.class), eq(1L));
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
}
