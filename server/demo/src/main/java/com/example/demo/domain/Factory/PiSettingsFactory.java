package com.example.demo.domain.Factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.domain.PiSettings;
import com.example.demo.domain.enums.EnumBoolean;
import com.example.demo.domain.enums.EnumVideoExt;
import com.example.demo.domain.enums.EnumVideoResolution;

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
	@Value("${pi.videosPath}")
	private String videosPath;
	@Value("${pi.pushPath}")
	private String pushPath;
	
	@Value("${pi.saveVideo}")
	private String saveVideo;
	
	@Value("${pi.videoExt}")
	private String videoExt;
	@Value("${pi.videoRes}")
	private String videoRes;
	
	@Value("${pi.bitrate}")
	private int bitrate;
	@Value("${pi.framerate}")
	private int framerate;
	@Value("${pi.rotation}")
	private int rotation;
	@Value("${pi.timeRecording}")
	private int timeRecording;
	
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
		
		toReturn.setVideosPath(videosPath);
		toReturn.setPushPath(pushPath);
		
		toReturn.setBitrate(bitrate);
		toReturn.setFramerate(framerate);
		toReturn.setRotation(rotation);
		toReturn.setTimeRecording(timeRecording);
		
		toReturn.setSaveVideo(EnumBoolean.valueOf(saveVideo));
		
		toReturn.setVideoExt(EnumVideoExt.valueOf(videoExt.toUpperCase()));
		toReturn.setVideoRes( EnumVideoResolution.valueOf(videoRes.toUpperCase()) );
		
		toReturn.setModel(model);
		toReturn.setWeights(weights);
		toReturn.setThreshold(threshold);
		
		List<String> classes = new ArrayList<String>();
		if (defaultClasses.contains(","))
			for(String s:defaultClasses.split(",")) 
				classes.add(s);
		else
			classes.add(defaultClasses);		
		toReturn.setClasses(classes);
		
		return toReturn;
	}
}
