package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Pi;
@Repository
public interface PiRepository extends JpaRepository<Pi, Long> {

}
