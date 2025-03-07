package com.example.account.service;

import java.util.List;
import java.util.Optional;

import com.example.account.model.Profile;

public interface ProfileService {
	
	Profile addProfile(Profile profile);
	List<Profile> getProfiles();
	Optional<Profile> findProfile(Long id);
	List<Profile> getUsersByProfileBio(String bio);
	
}
