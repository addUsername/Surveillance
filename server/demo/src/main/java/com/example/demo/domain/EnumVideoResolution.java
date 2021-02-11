package com.example.demo.domain;

public enum EnumVideoResolution {
	
	LOW(new int[]{320,320}),
	MEDIUM(new int[]{640,640}),
	HIGH(new int[]{1080,768}),
	VERY_HIGH(new int[]{1920,1080});
	
	private int[] size;
	
	private EnumVideoResolution(int[] size) {
		this.size = size;
	}

	public int[] getSize() {
		return size;
	}
	
}
