package com.example.demo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Pi;
import com.example.demo.domain.User;
import com.example.demo.dtos.RegisterDTO;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.Crypt;
import com.example.demo.security.Jwt;

@Service
public class AuthService {

	@Autowired
	private UserRepository repo;	

	@Autowired
	private DataSource dataSource;

	@Value("${secret.magicKey}")
	private String magicKey;
	
	@Value("${path.upload}")
	private String jwtSecret;
	
	@Value("${path.dump}")
	private String dumpPath;
	
	@Value("${path.upload}")
	private String uploadPath;
	
	public Boolean checkPin(Integer pin) {
		Integer userPin = repo.findByPin(pin).get().getPin();
		return (userPin != null)? true: false;
	}

	public boolean registerUser(@Valid RegisterDTO newUser) {
		
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
		
		repo.dumpDB(dumpPath);
		Crypt.encrypt(magicKey.getBytes(), new File(dumpPath), new File(dumpPath));
		return (new File(dumpPath));
	}

	public boolean validateAndDecryptData(MultipartFile file) {
		
		if(!validateData(file)) return false;
		
		try {
			Files.copy(file.getInputStream(),
					new File(uploadPath).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			
			Crypt.decrypt(magicKey.getBytes(),
					new File(uploadPath),
					new File(uploadPath));
			
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	private boolean validateData(MultipartFile file) {
		return !(file.getSize() < 1000 || file.getSize() > 5000);
	}
	
	public void loadDb() {
		try (Connection conn = dataSource.getConnection()) {
			Statement st = conn.createStatement();
			st.execute( "DROP ALL OBJECTS DELETE FILES;");
			InputStreamReader isr = new InputStreamReader( new FileInputStream(new File(uploadPath)));
			RunScript.execute(conn, (Reader) isr );			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

	public String generateToken(Integer pin) {
		// TODO Auto-generated method stub
		return Jwt.generateToken(jwtSecret.getBytes(),
								repo.findByPin(pin).get().getUsername());
	}
	
}
