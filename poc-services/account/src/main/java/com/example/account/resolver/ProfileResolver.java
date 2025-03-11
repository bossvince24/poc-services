package com.example.account.resolver;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.account.model.Profile;
import com.example.account.serviceImpl.ProfileServiceImpl;

@Controller
public class ProfileResolver {
	
	@Autowired
	private ProfileServiceImpl serviceImpl;
	
	public Profile addProfile(@Argument Profile profile) {
		return serviceImpl.addProfile(profile);
	}
	
	@QueryMapping
	public List<Profile> getProfiles() {
		return serviceImpl.getProfiles();
	}
	
	@QueryMapping
	public Profile findProfile(@Argument Long id) {
		Optional<Profile> profileOptional = serviceImpl.findProfile(id);
		return profileOptional.get();
	}
}
