package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Pi;
@Repository
public interface PiRepository extends JpaRepository<Pi, Long> {

	@Query(value="SELECT id, status FROM PI", nativeQuery = true)
	List<String[]> findAllIds();
}
