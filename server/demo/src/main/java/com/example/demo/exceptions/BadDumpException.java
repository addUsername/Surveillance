package com.example.demo.exceptions;

public class BadDumpException extends RuntimeException {

	public BadDumpException() {
		super("Dump file seems to be corrupted, information is lost");
	}
}
