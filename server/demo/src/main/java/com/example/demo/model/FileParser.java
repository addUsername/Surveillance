package com.example.demo.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import org.springframework.stereotype.Component;
/**
 * Model is gonna be a lot more complex:
 * - User.class
 * - Raspberry.class
 * - VideoFormat enum
 * - AUTH
 * - JPA
 * - ??
 * @author SERGI
 *
 */
@Component
public class FileParser {

	private SynchronousQueue<byte[]> buffer;
	private BufferedOutputStream bos;
	private String path = "video";
	private final String MJPEG = ".mjpg";
	private final String H264 = ".h264";
	private int id;
	
	public FileParser() {
		System.err.println("file parse instance created");
	}
	
	public void add(byte[] fragment) {
		try {
			buffer.put(fragment);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		buffer = new SynchronousQueue<byte[]>();
		this.id = id;		
	}
	
	public void writeStream(OutputStream responseOs, int videoId, String type) {
		
		if(type.equals(H264)) {
			writeStreamH264(responseOs);
			return;
		}
		byte[] bytes = null;
		while(true) {			
			try {
				bytes = buffer.take();
				if (bytes == null) return;
				responseOs.write((
							"--BoundaryString\r\n" +
							"Content-type: image/jpeg\r\n" +
							"Content-Length: " +bytes.length +
							"\r\n\r\n").getBytes());
				responseOs.write(bytes);
				responseOs.write("\r\n\r\n".getBytes());
				responseOs.flush();
				Thread.sleep(1000/20);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("interrupted");
			}			
		}		
	}

	private void writeStreamH264(OutputStream responseOs) {
		
		byte[] bytes = null;
		while(true) {			
			try {
				bytes = buffer.take();
				if (bytes == null) return;
				responseOs.write(bytes);
				responseOs.flush();
				Thread.sleep(1000/20);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("interrupted");
			}			
		}
	}
}
