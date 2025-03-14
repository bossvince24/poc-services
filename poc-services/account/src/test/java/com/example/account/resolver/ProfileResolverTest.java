package com.example.account.resolver;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.example.account.model.Profile;
import com.example.account.model.User;
import com.example.account.serviceImpl.ProfileServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ProfileResolverTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private ProfileServiceImpl profileService;
	
	@DisplayName("Get Profiles Test")
	@Test
	public void testGetProfiles() throws Exception {
		
		String query = "{ \"query\": \"{ getProfiles { id bio profilePictureUrl } }\" }";
	    
		Profile profiles = new Profile();
		profiles.setId(1L);
		profiles.setBio("Software Engineer");
		profiles.setProfilePictureUrl("abc@def.com");
		
		List<Profile> profileList = Arrays.asList(profiles);
		
		when(profileService.getProfiles()).thenReturn(profileList);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.getProfiles[0].bio").value(profileList.get(0).getBio()))
		.andExpect(jsonPath("$.data.getProfiles[0].profilePictureUrl").value(profileList.get(0).getProfilePictureUrl()));
		
		verify(profileService, times(1)).getProfiles();
	}
	
	@DisplayName("Find Profile Test")
	@Test
	public void testFindProfile() throws Exception {
		
		String query = "{ \"query\": \" { findProfile (id: 1) { id user { name email } bio profilePictureUrl } }\" }";
		
		User user = new User();
		user.setName("John Doe");
		user.setEmail("john.doe@example.com");
		
		Profile profiles = new Profile();
		profiles.setId(1L);
		profiles.setUser(user);
		profiles.setBio("Software Engineer");
		profiles.setProfilePictureUrl("abc@def.com/jpg");
		
		when(profileService.findProfile(1L)).thenReturn(Optional.of(profiles));
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.findProfile.user.name").value(profiles.getUser().getName()))
		.andExpect(jsonPath("$.data.findProfile.user.email").value(profiles.getUser().getEmail()))
		.andExpect(jsonPath("$.data.findProfile.bio").value(profiles.getBio()))
		.andExpect(jsonPath("$.data.findProfile.profilePictureUrl").value(profiles.getProfilePictureUrl()));
		
		verify(profileService, times(1)).findProfile(profiles.getId());
	}
}
