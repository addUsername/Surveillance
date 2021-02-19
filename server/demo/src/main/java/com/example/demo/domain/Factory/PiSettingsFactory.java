package com.example.demo.domain.Factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.domain.EnumStatus;
import com.example.demo.domain.EnumVideoExt;
import com.example.demo.domain.EnumVideoResolution;
import com.example.demo.domain.PiSettings;

import lombok.Data;
/**
 * Ugly but works, its mission is set default values from application.properties to @Entity PiSettings
 * @author SERGI
 *
 */
@Component
@Data
public class PiSettingsFactory {

	@Value("${pi.host}")
	private String host;
	@Value("${pi.iniPath}")
	private String iniPath;
	@Value("${pi.connectPath}")
	private String connectPath;
	@Value("${pi.sendPath}")
	private String sendPath;
	@Value("${pi.subscribePath}")
	private String subscribePath;

	private EnumVideoExt videoExt = EnumVideoExt.H264;
	private EnumVideoResolution videoRes = EnumVideoResolution.MEDIUM;
	@Value("${pi.bitrate}")
	private int bitrate;
	
	// detection
	@Value("${pi.model}")
	private String model;
	@Value("${pi.weights}")
	private String weights;
	private List<String> classes = new ArrayList<String>(); //LOOK thiss
	@Value("${pi.threshold}")
	private String threshold;
	@Value("${pi.classes}")
	private String defaultClasses;
	
	public PiSettings getInstance() {
		PiSettings toReturn = new PiSettings();
		toReturn.setHost(host);
		toReturn.setIniPath(iniPath);
		toReturn.setConnectPath(connectPath);
		toReturn.setSendPath(sendPath);
		toReturn.setSubscribePath(subscribePath);
		
		toReturn.setVideoExt(videoExt);
		toReturn.setVideoRes(videoRes);
		toReturn.setBitrate(bitrate);
		
		toReturn.setModel(model);
		toReturn.setWeights(weights);
		toReturn.setThreshold(threshold);
		List<String> classes = new ArrayList<String>();
		classes.add(defaultClasses);
		toReturn.setClasses(classes);
		
		toReturn.setStatus(EnumStatus.OFF);
		
		return toReturn;
	}
}
