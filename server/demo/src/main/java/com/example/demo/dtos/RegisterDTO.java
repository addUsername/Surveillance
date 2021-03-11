package com.example.demo.dtos;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor@Getter@Setter
public class RegisterDTO {
	@NotNull
	@Size(min=2, max=30, message = "wrong username size")
	private String username;
	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{6,15}$", message = "pass must contain: lowercase, uppercase, number and special char")
	private String pass;
	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{6,15}$", message = "pass must contain: lowercase, uppercase, number and special char")
	private String pass2;
	@NotNull
	@Min(value = 10000, message = "cannot be less than 10000")
	@Max(value = 99999, message = "cannot be more than 100000")
	private Integer pin;
	@NotNull
	@Email
	private String email;	
	@AssertTrue
	public boolean isValid() {
		return getPass().equals(getPass2());
	}
}
