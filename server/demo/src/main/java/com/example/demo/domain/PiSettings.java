package com.example.demo.domain;

public class PiSettings {

	// http connection
	private long id; //mac?
	private String iniPath;
	
	// 
	private String conectPath;
	private String sendPath;
	private String subscribePath;

	// video streaming
	private EnumVideoExt videoExt;	
	private int bitrate;
	private EnumVideoResolution videoRes;
	
	// detection
	private String model;
	private String config;
	private String[] classes;
	private int threshoold;
}
