package com.example.demo.services;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pi;
import com.example.demo.domain.User;
import com.example.demo.dtos.RegisterDTO;
import com.example.demo.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository repo;
	
	public Boolean checkPin(Integer pin) {
		Integer userPin = repo.findByPin(pin).get();
		return (userPin != null)? true: false;
	}

	public boolean registerUser(@Valid RegisterDTO newUser) {
		

		if(!newUser.getPass().equals(newUser.getPass2())) {
			return false;
		}		
		if(repo.existsByUsername(newUser.getUsername())) {
			return false;
		}
		
		User user = new User();
		user.setUsername(newUser.getUsername());
		user.setPass(newUser.getPass());
		user.setPin(newUser.getPin());
		user.setCameras(new ArrayList<Pi>());
		
		repo.save(user);		
		return false;
	}
	
}
