package com.example.demo.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.enums.EnumVideoExt;
/**
 * @author SERGI
 *
 */
@Component
public class FileParser {

	@Autowired
	private PushNotificationService pns;
	@Value("${path.imgPush}")
	private String IMGPUSHPATH;
	
	//private HashMap<Integer,LinkedBlockingQueue<byte[]>> videos;
	private HashMap<Integer,ArrayBlockingQueue<byte[]>> videos;
	private HashMap<Integer, byte[]> files;
	
	
	public FileParser() {
		System.err.println("file parse instance created");
		this.videos= new HashMap<Integer, ArrayBlockingQueue<byte[]>>();
		this.files = new HashMap<Integer, byte[]>();
	}
	
	public void add(byte[] fragment, int videoId) {
		
		try {
			videos.get(videoId).put(fragment);
			System.out.println("ADD");
		} catch (InterruptedException e) {
			System.out.println("add interrupted");
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
	public void iniUpload(int id) { videos.put(id, new ArrayBlockingQueue<byte[]>(1024)); }
	public void iniUploadFile(int id) { files.put(id, null); }
	
	public void writeStream(OutputStream responseOs, int videoId, String type) {
		
		
		if(type.equals(EnumVideoExt.H264.toString())) {
			writeStreamH264(responseOs, videoId);
			return;
		}
		
		byte[] bytes = null;
		ArrayBlockingQueue<byte[]> buffer = videos.get(videoId);
		while(true) {			
			try {
				bytes = buffer.take();
				if (bytes == null) return;
				//this should be outside the while loop, no?
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
		ArrayBlockingQueue<byte[]> buffer = videos.get(videoId);
		while(true) {
			try {
				System.out.println("READ");
				bytes = buffer.remove();
				if (bytes == null) return;
				responseOs.write(bytes);
				responseOs.flush();
				Thread.sleep(1000/10); //TODO fps
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				System.out.println("read interrupted");
			}			
		}
	}

	public void savePushImage(String rd, InputStream inputStream) {
		
	    try (OutputStream output = new FileOutputStream(new java.io.File(IMGPUSHPATH+rd+".jpg"))) {
	        inputStream.transferTo(output);
	    } catch (IOException ioException) {
	        ioException.printStackTrace();
	    }		
	}

	public java.io.File getPushImg(String name) {
		
		java.io.File toReturn = null;
		if(pns.isImgAccesible(name)) {
			 toReturn = new java.io.File(IMGPUSHPATH + name + ".jpg");
			 toReturn.deleteOnExit();
		}
		return toReturn;
	}

	public byte[] downloadFile(int id) {
		try {
			return files.get(id);
		}catch (Exception e) {
			return null;
		}
		
	}
	public void saveScreenshot(int id, MultipartFile file) throws IOException { files.put(id, file.getBytes()); }
}
