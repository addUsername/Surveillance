package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pi;
import com.example.demo.domain.User;
import com.example.demo.dtos.HomeDTO;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;

	public HomeDTO getHome() {
		
		List<User> uList = repo.findAll();
		if(uList.isEmpty()) return null;
		
		
		List<Long> ids = new ArrayList<Long>();		
		for(Pi pi: uList.get(0).getCameras()) {
			ids.add(pi.getId());
		}
		HomeDTO toReturn = new HomeDTO();
		toReturn.setPi_ids(ids);
		return toReturn;
	}

}
