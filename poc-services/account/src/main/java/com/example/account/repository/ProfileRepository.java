package com.example.account.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.account.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

//	List<Profile> findByBioContainingIgnoreCase(String bio) throws UserNotFoundException;

}
