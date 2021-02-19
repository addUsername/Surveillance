package com.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	Optional<User> findByUsername(String username);
	Optional<User> findByPin(Integer pin);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
	@Query(value="SCRIPT TO ?1 ;", nativeQuery = true)
	List<String> dumpDB(String path);	
}