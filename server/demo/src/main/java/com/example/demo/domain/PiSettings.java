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

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
public class PiSettings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
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

	@Enumerated(EnumType.STRING)
	private EnumVideoExt videoExt = EnumVideoExt.H264;
	@Enumerated(EnumType.STRING)
	private EnumVideoResolution videoRes = EnumVideoResolution.MEDIUM;
	@Value("${pi.bitrate}")
	private int bitrate;
	
	// detection
	@Value("${pi.model}")
	private String model;
	@Value("${pi.weights}")
	private String weights;
	@ElementCollection
	private List<String> classes = new ArrayList<String>(); //LOOK thiss
	@Value("${pi.threshold}")
	private int threshoold;
	@Transient
	@Value("${pi.classes}")
	private String defaultClasses;
	
	public PiSettings() {
		this.classes.add(defaultClasses);
	}
	/**
	 * TODO
	 * refection powah https://stackoverflow.com/a/14114122/13771772
	 * @return
	 */
	public String toStringFile() {
		String toReturn = "";
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
