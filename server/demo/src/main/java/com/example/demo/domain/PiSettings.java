package com.example.demo.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PiSettings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String host;
	private String iniPath;
	private String connectPath;
	private String sendPath;
	private String subscribePath;

	@Enumerated(EnumType.STRING)
	private EnumVideoExt videoExt = EnumVideoExt.H264;
	@Enumerated(EnumType.STRING)
	private EnumVideoResolution videoRes = EnumVideoResolution.MEDIUM;
	private int bitrate;
	
	// detection
	private String model;
	private String weights;
	@ElementCollection
	private List<String> classes; //LOOK thiss
	private String threshold;
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	
	public PiSettings() {		
	}
	/**
	 * TODO
	 * refection powah https://stackoverflow.com/a/14114122/13771772
	 * @return
	 */
	public String toStringFile(String id_RPi) {
		String toReturn = "id="+id_RPi+"\n";
		 Class<?> c = this.getClass();
		 Field[] fields = c.getDeclaredFields();
		 for( Field field : fields ){
		      try {
		           toReturn += field.getName().toString() + "=" + field.get(this)+"\n";
		      } catch (IllegalArgumentException e1) {
		      } catch (IllegalAccessException e1) {
		      }
		 }
		 toReturn.substring(0,toReturn.length() - 1);
		 System.out.println(toReturn);
		return toReturn;
	}
}
