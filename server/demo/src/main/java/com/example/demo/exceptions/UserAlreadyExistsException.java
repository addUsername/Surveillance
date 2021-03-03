package com.example.demo.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException() {
		super("Username / email already exists ");
	}
}
