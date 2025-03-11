package com.example.account.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Profile> getProfiles() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Optional<Profile> findProfile(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}
}
