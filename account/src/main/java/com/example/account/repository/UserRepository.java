package com.example.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.account.exception.UserNotFoundException;
import com.example.account.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByNameContainingIgnoreCase(String name) throws UserNotFoundException;

}
