package com.eirb.projets9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Application;

import com.eirb.projets9.scanner.BeaconRecord;
import com.eirb.projets9.scanner.NotificationService;

public class ReferenceApplication extends Application {

//	public BackgroundPowerSaver mBackgroundPowerSaver;	// Seems to kill the background service if the screen is off
	
	public static final int TIME_TO_BE_NOTIFIED = 10;
	public static final double DISTANCE_TO_BE_NOTIFIED = 2;
	public static ArrayList<BeaconRecord> records;
	public static NotificationService notificationService;
	public static String conferenceFilePath;
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("onCreate Application");
//		mBackgroundPowerSaver = new BackgroundPowerSaver(this);
		records = new ArrayList<BeaconRecord>();
		conferenceFilePath = getFilesDir().getAbsolutePath().concat("/conference.json");
		
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
}
