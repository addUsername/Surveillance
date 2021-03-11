package com.example.demo.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PinDTO {

	@Min(value = 10000, message = "cannot be less than 10000")
	@Max(value = 99999, message = "cannot be more than 100000")
	private Integer pin;
	@NotNull
	private String token;
}
