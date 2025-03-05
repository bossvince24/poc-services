package com.example.account.resolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.account.model.Profile;
import com.example.account.model.User;
import com.example.account.service.ProfileService;
import com.example.account.service.UserService;
//import com.example.account.serviceImpl.ProfileServiceImpl;
//import com.example.account.serviceImpl.UserServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserResolverTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private UserService userService;
	
	@Mock
	private ProfileService profileService;
	
	@DisplayName("Add User Test")
	@Test
	public void testAddUser() throws Exception {
		
		String graphquery = "{ \"query\": \"mutation { addUser (name: \\\"John Doe\\\", "
                + "email: \\\"john.doe@example.com\\\", bio: \\\"Software Developer\\\", "
                + "profilePictureUrl: \\\"http://example.com/profile.jpg\\\") "
                + "{ id name email profile { bio profilePictureUrl } } }\" }";

		
		Profile mockProfile = new Profile();
		mockProfile.setBio("Software Developer");
		mockProfile.setProfilePictureUrl("http://example.com/profile.jpg");
		
		User mockUser = new User();
		mockUser.setName("John");
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
		
		verify(profileService, times(1)).addProfile(any(Profile.class));
		verify(profileService, times(1)).addProfile(any(Profile.class));

	}

}
