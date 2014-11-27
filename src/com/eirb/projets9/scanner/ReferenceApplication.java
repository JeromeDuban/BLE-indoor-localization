package com.eirb.projets9.scanner;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import android.app.Application;

public class ReferenceApplication extends Application {

	public BeaconManager mBeaconManager;
	public BackgroundPowerSaver mBackgroundPowerSaver;
	
	public void onCreate() {
		mBackgroundPowerSaver = new BackgroundPowerSaver(this);
	};
	
}
