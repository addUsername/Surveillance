package com.example.demo.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByUsername(String username);
	Optional<Integer> findByPin(Integer pin);
	boolean existsByUsername(String username);
}
