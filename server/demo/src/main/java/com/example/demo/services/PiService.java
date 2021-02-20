package com.example.demo.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.EnumStatus;
import com.example.demo.domain.EnumVideoExt;
import com.example.demo.domain.EnumVideoResolution;
import com.example.demo.domain.Pi;
import com.example.demo.domain.PiSettings;
import com.example.demo.domain.User;
import com.example.demo.domain.Factory.PiSettingsFactory;
import com.example.demo.dtos.PiDTO;
import com.example.demo.dtos.PiSettingsDTO;
import com.example.demo.repositories.PiRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class PiService {

	@Autowired
	private PiRepository repo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PiSettingsFactory piSettingFactory;
	
	public String getSettingsFile(Long id) {
		
		Pi pi = repo.findById(id).get();
		if(pi == null) return null;		
		return pi.getPiSettings().toStringFile(""+id);
	}

	public boolean addRPi(@Valid PiDTO pi) {
		
		List<User> uList = userRepo.findAll();
		if(uList.isEmpty()) return false;
		
		Pi p = new Pi();
		p.setAlias(pi.getAlias());
		p.setLocation(pi.getLocation());
		p.setPiSettings(piSettingFactory.getInstance());
		p.setUser(uList.get(0));
		repo.save(p);
		return true;		
	}

	public boolean updatePiSettings(@Valid PiSettingsDTO piSettings) {
		
		Pi pi = repo.findById(piSettings.getId()).get();
		if(pi == null) return false;
		
		PiSettings piS = pi.getPiSettings();
		
		if(piSettings.getBitrate() != null) piS.setBitrate(Integer.parseInt(piSettings.getBitrate()));
		if(piSettings.getVideoRes() != null) piS.setVideoRes(EnumVideoResolution.valueOf(piSettings.getVideoRes()));
		if(piSettings.getVideoExt() != null) piS.setVideoExt(EnumVideoExt.valueOf(piSettings.getVideoExt()));
		
		if(piSettings.getModel() != null) piS.setModel(piSettings.getModel());
		if(piSettings.getWeights() != null) piS.setWeights(piSettings.getWeights());
		if(piSettings.getThreshold() != null) piS.setThreshold(piSettings.getThreshold());
		
		pi.setPiSettings(piS);
		repo.save(pi);		
		return true;
	}

	public boolean changeStatusToUP(int id) {
		Pi pi = repo.findById(Long.parseLong(""+id)).get();
		if(pi == null) return false;
		pi.setStatus(EnumStatus.UP);		
		return true;
	}

	public String getVideoExt(int id) {
		Pi pi = repo.findById(Long.parseLong(""+id)).get();
		if(pi == null) return null;
		return pi.getPiSettings().getVideoExt().toString();
		
	}

}
