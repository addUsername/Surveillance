package com.example.demo.domain;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.demo.domain.enums.EnumBoolean;
import com.example.demo.domain.enums.EnumVideoExt;
import com.example.demo.domain.enums.EnumVideoResolution;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PiSettings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	// uris
	private String host;
	private String iniPath;
	private String connectPath;
	private String sendPath;
	private String subscribePath;
	
	private String videosPath;
	private String pushPath;
	
	@Enumerated(EnumType.STRING)
	private EnumBoolean saveVideo = EnumBoolean.False;
	// video
	@Enumerated(EnumType.STRING)
	private EnumVideoExt videoExt = EnumVideoExt.H264;
	@Enumerated(EnumType.STRING)
	private EnumVideoResolution videoRes = EnumVideoResolution.MEDIUM;
	private int bitrate;
	
	private int framerate;
	private int rotation;
	private int timeRecording;
	
	// detection
	private String model;
	private String weights;
	@ElementCollection
	private List<String> classes;
	private String threshold;
	
	public PiSettings() {		
	}
	/**
	 * Reflection powaah https://stackoverflow.com/a/14114122/13771772
	 * @return String
	 */
	public String toStringFile(String id_RPi) {
		String toReturn = "id="+id_RPi+"\n";
		Class<?> c = this.getClass();
		Field[] fields = c.getDeclaredFields();
		for( Field field : fields ){
			try {
				if(!field.getName().toString().equals("id"))
					toReturn += field.getName() + "=" + field.get(this)+"\n";
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		toReturn.substring(0,toReturn.length() - 1);
		return toReturn;
	}
}
