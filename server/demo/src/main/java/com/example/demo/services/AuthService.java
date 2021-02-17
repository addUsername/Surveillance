package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Pi;
import com.example.demo.domain.User;
import com.example.demo.dtos.RegisterDTO;
import com.example.demo.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository repo;

	@Value("${secret.magicKey}")
	private String magicKey;
	
	@Value("${path.dump}")
	private String dumpPath;
	
	@Value("${path.upload}")
	private String uploadPath;
	
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
		CriptService.encrypt(magicKey.getBytes(), new File(dumpPath), new File(dumpPath));
		return (new File(dumpPath));
	}

	public File validateAndDecryptData(MultipartFile file) {
		try {
			/*
			 * 	VALIDATE FILE
			 * 	System.out.println(file.getSize());
				System.out.println(file.getBytes());
				System.out.println(file.getContentType());
				System.out.println(file.getName());
			 */
			Files.copy(file.getInputStream(), new File(uploadPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
			CriptService.decrypt(magicKey.getBytes(), new File(uploadPath), new File(uploadPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		repo.loadDB(uploadPath);
		return new File(uploadPath);
	}
	
}
