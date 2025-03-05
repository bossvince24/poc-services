package com.example.account.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.account.exception.UserNotFoundException;
import com.example.account.model.Profile;
import com.example.account.repository.ProfileRepository;
import com.example.account.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository repository;

	@Override
	public Profile addProfile(Profile profile) {
		// TODO Auto-generated method stub
		return repository.save(profile);
	}

	@Override
	public List<Profile> getUserProfiles() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Optional<Profile> findUserProfile(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	public List<Profile> getUsersByProfileBio(String bio) {
		// TODO Auto-generated method stub
		List<Profile> profiles = repository.findByBioContainingIgnoreCase(bio);
		
		if (profiles.isEmpty()) {
			throw new UserNotFoundException("No users found with bio: " + bio);
		}
		return profiles;
	}

}
