package com.example.demo.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.SynchronousQueue;

import org.springframework.stereotype.Component;

import com.example.demo.domain.EnumVideoExt;
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

	private HashMap<Integer,SynchronousQueue<byte[]>> videos;
	
	
	public FileParser() {
		System.err.println("file parse instance created");
		this.videos= new HashMap<Integer, SynchronousQueue<byte[]>>();
	}
	
	public void add(byte[] fragment, int videoId) {
		try {
			videos.get(videoId).put(fragment);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/*
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
	*/
	public void iniUpload(int id) {		
		videos.put(id, new SynchronousQueue<byte[]>());
	}
	
	public void writeStream(OutputStream responseOs, int videoId, String type) {
		
		if(type.equals(EnumVideoExt.H264.toString())) {
			writeStreamH264(responseOs, videoId);
			return;
		}
		
		byte[] bytes = null;
		SynchronousQueue<byte[]> buffer = videos.get(videoId);
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
				//e.printStackTrace();
				System.out.println("interrupted");
			}			
		}		
	}

	private void writeStreamH264(OutputStream responseOs, int videoId) {
		
		byte[] bytes = null;
		SynchronousQueue<byte[]> buffer = videos.get(videoId);
		while(true) {			
			try {
				bytes = buffer.take();
				if (bytes == null) return;
				responseOs.write(bytes);
				responseOs.flush();
				Thread.sleep(1000/10); //TODO fps
			} catch (IOException | InterruptedException e) {
				//e.printStackTrace();
				System.out.println("interrupted");
			}			
		}
	}
}
