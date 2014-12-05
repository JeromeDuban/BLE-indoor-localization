package com.eirb.projets9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Application;

import com.eirb.projets9.objects.Conference;
import com.eirb.projets9.scanner.BeaconRecord;
import com.eirb.projets9.scanner.NotificationService;

public class ReferenceApplication extends Application {

//	public BackgroundPowerSaver mBackgroundPowerSaver;	// Seems to kill the background service if the screen is off
	
	public static final int TIME_TO_BE_NOTIFIED = 10;
	public static final double DISTANCE_TO_BE_NOTIFIED = 2;
	public static ArrayList<BeaconRecord> records;
	public static NotificationService notificationService;
	public static String conferenceFile;
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("onCreate Application");
//		mBackgroundPowerSaver = new BackgroundPowerSaver(this);
		records = new ArrayList<BeaconRecord>();
		conferenceFile = getFilesDir().getAbsolutePath().concat("/conference");
		
	};	
	
	public static void setNotificationService(NotificationService service){
		notificationService = service;
	}
	
	public static void recordAdded(){
		notificationService.recordCallback();
	}
	
	public static void writeToFile(String content, String path) {
		 
		FileOutputStream fop = null;
		File file;
 
		try {
 
			file = new File(path);
			fop = new FileOutputStream(file);
 
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = content.getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String readFromFile(String path) {
		 
		BufferedReader br = null;
		String file = "";
 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(path));
 
			while ((sCurrentLine = br.readLine()) != null) {
				file = file.concat(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return file;
	}
	
	public static void serializeConf(Conference conf){
		 
	   try{
 
		FileOutputStream fout = new FileOutputStream(conferenceFile);
		ObjectOutputStream oos = new ObjectOutputStream(fout);   
		oos.writeObject(conf);
		oos.close();
		System.out.println("Done");
 
	   }catch(Exception ex){
		   ex.printStackTrace();
	   }
	}
	
	 public static Conference deserializeAddress(){
		 
		   Conference conf; 
		   try{
	 
			   FileInputStream fin = new FileInputStream(conferenceFile);
			   ObjectInputStream ois = new ObjectInputStream(fin);
			   conf = (Conference) ois.readObject();
			   ois.close();
	 
			   return conf;
	 
		   }catch(Exception ex){
			   ex.printStackTrace();
			   return null;
		   } 
	   } 
	
}
