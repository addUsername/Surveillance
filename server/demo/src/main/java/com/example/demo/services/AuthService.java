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
import com.example.demo.dtos.PinDTO;
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
	
	@Autowired
	private Jwt jwt;

	@Value("${secret.magicKey}")
	private String magicKey;
	
	@Value("${path.dump}")
	private String dumpPath;
	
	@Value("${path.upload}")
	private String uploadPath;
	
	private String fcmtToken;
	
	public String getFcmtToken() { return fcmtToken; }
	public Boolean checkPin(PinDTO pin) {
		try {
			repo.findByPin(pin.getPin()).get().getPin();
			fcmtToken = pin.getToken();
			return true;
		}catch (Exception e) {
			return false;
		}
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
		
		repo.prepareForDumpDB();
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
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// TODO Check this file sizes, cool bug i've eaten max size is ~5Mb
	private boolean validateData(MultipartFile file) {
		return !(file.getSize() < 1000 || file.getSize() > 50000);
	}
	
	public void loadDb() {
		InputStreamReader isr;
		try (Connection conn = dataSource.getConnection()) {
			Statement st = conn.createStatement();
			st.execute( "DROP ALL OBJECTS DELETE FILES;");
			isr = new InputStreamReader( new FileInputStream(new File(uploadPath)));
			RunScript.execute(conn, (Reader) isr );	
			isr.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String generateToken(Integer pin) {
		String username = repo.findByPin(pin).get().getUsername();
		if(username == null) return null;
		return jwt.generateToken(username);
	}
	
}
