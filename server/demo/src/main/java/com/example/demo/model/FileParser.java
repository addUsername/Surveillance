package com.example.demo.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Component;
/**
 * Model is gonna be a lot more complex:
 * - User.class
 * - Raspberry.class
 * - AUTH
 * - JPA
 * - ??
 * @author SERGI
 *
 */
@Component
public class FileParser {

	private Queue<byte[]> buffer;
	private BufferedOutputStream bos;
	private String path = "video.mjpg";
	private int id;
	
	public FileParser() {
		System.err.println("file parse instance created");
	}
	
	public void add(byte[] fragment) {
		buffer.add(fragment);
	}

	public boolean save() {
		try {
			bos = new BufferedOutputStream(new FileOutputStream(id+path)); 
			for(byte[] fragment: buffer) {			
					bos.write(fragment);
			}
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new File(path).exists();
	}

	public void iniUpload(int id) {
		buffer = new LinkedList<byte[]>();
		this.id = id;
		
	}
}
