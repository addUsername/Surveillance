package com.example.demo.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pi;
import com.example.demo.domain.PiSettings;
import com.example.demo.domain.User;
import com.example.demo.dtos.PiDTO;
import com.example.demo.repositories.PiRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class PiService {

	@Autowired
	private PiRepository repo;
	@Autowired
	private UserRepository userRepo;
	
	public String getSettingsFile(Long id) {
		
		Pi pi = repo.findById(id).get();
		if(pi == null) return null;		
		return pi.getPiSettings().toStringFile();
	}

	public boolean addRPi(@Valid PiDTO pi) {
		
		List<User> uList = userRepo.findAll();
		if(uList.isEmpty()) return false;
		
		Pi p = new Pi();
		p.setAlias(pi.getAlias());
		p.setLocation(pi.getLocation());
		p.setPiSettings(new PiSettings());
		
		User u = uList.get(0);
		List <Pi> RPis = u.getCameras();
		RPis.add(p);
		u.setCameras(RPis);
		userRepo.save(u);
		return true;		
	}
}
