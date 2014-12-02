package com.eirb.projets9;

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
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("onCreate Application");
//		mBackgroundPowerSaver = new BackgroundPowerSaver(this);
		records = new ArrayList<BeaconRecord>();
	};	
	
	public static void setNotificationService(NotificationService service){
		notificationService = service;
	}
	
	public static void recordAdded(){
		notificationService.recordCallback();
	}
}
