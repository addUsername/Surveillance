package com.example.demo.domain.enums;

public enum EnumVideoExt {
	
	MJPEG(".mjpg"), H264(".h264");
	
	private String ext;
	
	private EnumVideoExt(String ext) {
		this.ext = ext;
	}
	public String getExt() {
		return ext;
	}
	
}
