package com.example.demo.dtos;

import java.util.List;

import lombok.Data;

@Data
public class PiSettingsDTO {
	private Long id; // RPI ID NOT SETTIGS ID
	// video
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
	private String threshold=null;
}
