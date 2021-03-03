package com.example.demo.exceptions;

public class BadLoginException extends RuntimeException {

	public BadLoginException() {
		super("Wrong username/pin");
	}
}
