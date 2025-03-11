package com.example.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.account.exception.UserNotFoundException;
import com.example.account.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByNameContainingIgnoreCase(String name) throws UserNotFoundException;

	@Query("SELECT u FROM User u JOIN u.profile p WHERE p.bio = :bio")
	List<User> findByBioContainingIgnoreCase(@Param("bio") String bio) throws UserNotFoundException;

}
