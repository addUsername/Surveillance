package com.example.demo.dtos;

import lombok.Data;

@Data
public class PiSettingsDTO {

	private Long id = null;
	private String videoExt = null;
	private String videoRes = null;
	private String bitrate = null;
	
	private String model = null;
	private String weights = null;
	private String threshold = null;
}
