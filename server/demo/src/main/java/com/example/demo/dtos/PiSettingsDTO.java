package com.example.demo.dtos;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.demo.domain.enums.EnumBoolean;
import com.example.demo.domain.enums.EnumVideoExt;
import com.example.demo.domain.enums.EnumVideoResolution;

import lombok.Data;

@Data
public class PiSettingsDTO {
	private Long id; // RPI ID NOT SETTIGS ID
	// uris
	private String host=null;
	private String iniPath=null;
	private String connectPath=null;
	private String sendPath=null;
	private String subscribePath=null;
	private String videosPath=null;
	private String pushPath=null;
	private String saveVideo = null;
	private String videoExt = null;
	private String videoRes = null;
	private String bitrate = null;
	
	private String framerate=null;
	private String rotation=null;
	private String timeRecording=null;
	
	// detection
	private String model=null;
	private String weights=null;
	private List<String> classes=null;
	private String threshold=null;;
}
