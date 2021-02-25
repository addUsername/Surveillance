package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.HomeDTO;
import com.example.demo.dtos.HomePiDTO;
import com.example.demo.repositories.PiRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private PiRepository piRepo;

	public HomeDTO getHome() {
		
		List<HomePiDTO> list = new ArrayList<HomePiDTO>();		
		
		for(String[] s: piRepo.findAllIds()) {
			list.add(new HomePiDTO(Long.parseLong(s[0]),s[1]));
		}
		HomeDTO toReturn = new HomeDTO();
		toReturn.setPi_ids(list);
		return toReturn;
	}
	
	public String getUsername() {
		
		return repo.findAll().get(0).getUsername();
	}

}
