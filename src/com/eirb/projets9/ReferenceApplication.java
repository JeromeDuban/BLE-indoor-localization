package com.eirb.projets9;

import java.util.ArrayList;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import com.eirb.projets9.scanner.BeaconRecord;

import android.app.Application;

public class ReferenceApplication extends Application {

//	public BackgroundPowerSaver mBackgroundPowerSaver;
	
	public static ArrayList<BeaconRecord> records;
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("onCreate Application");
//		mBackgroundPowerSaver = new BackgroundPowerSaver(this);
		records = new ArrayList<BeaconRecord>();
	};	
}
