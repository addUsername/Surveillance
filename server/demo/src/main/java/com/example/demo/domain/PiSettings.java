package com.example.demo.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @Getter @Setter
public class PiSettings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String iniPath;
	private String conectPath;
	private String sendPath;
	private String subscribePath;

	@Enumerated(EnumType.STRING)
	private EnumVideoExt videoExt;
	@Enumerated(EnumType.STRING)
	private EnumVideoResolution videoRes;
	private int bitrate;
	
	// detection
	private String model;
	private String weights;
	@ElementCollection
	private List<String> classes; //LOOK thiss
	private int threshoold;
}
