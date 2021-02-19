package com.example.demo.dtos;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class PiDTO {
	@NotNull
	private String alias;
	@NotNull
	private String location;
}
