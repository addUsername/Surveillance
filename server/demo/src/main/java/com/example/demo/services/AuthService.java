package com.example.demo.services;

import java.io.File;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pi;
import com.example.demo.domain.User;
import com.example.demo.dtos.RegisterDTO;
import com.example.demo.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository repo;

	@Value("${path.dump}")
	private String dumpPath;
	
	public Boolean checkPin(Integer pin) {
		Integer userPin = repo.findByPin(pin).get();
		return (userPin != null)? true: false;
	}

	public boolean registerUser(@Valid RegisterDTO newUser) {
		

		if(!newUser.getPass().equals(newUser.getPass2())) {
			return false;
		}		
		if(repo.existsByUsername(newUser.getUsername()) || repo.existsByEmail(newUser.getEmail())) {
			return false;
		}
		
		// TODO build pattern with lombok
		User user = new User();
		user.setUsername(newUser.getUsername());
		user.setPass(newUser.getPass());
		user.setPin(newUser.getPin());
		user.setEmail(newUser.getEmail());
		user.setCameras(new ArrayList<Pi>());
		
		repo.save(user);		
		return true;
	}

	public File getDump() {
		System.err.println(dumpPath);
		repo.dumpDB(dumpPath);
		// encript
		return (new File(dumpPath));
	}
	
}
