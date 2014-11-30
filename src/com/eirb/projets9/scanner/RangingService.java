package com.eirb.projets9.scanner;

import java.util.ArrayList;
import java.util.Collection;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.RegionBootstrap;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.widget.TextView;
import android.widget.Toast;

import com.eirb.projets9.ReferenceApplication;

public class RangingService extends Service implements BeaconConsumer, RangeNotifier{
	
	static Context c;
	
	private BeaconManager mBeaconManager;
	private Region mAllBeaconsRegion;
	
	public ArrayList<BeaconRecord> records;
	
	@SuppressWarnings("unused")	private BackgroundPowerSaver mBackgroundPowerSaver;
	@SuppressWarnings("unused")	private RegionBootstrap mRegionBootstrap;
	
    @Override
    public IBinder onBind( final Intent arg0 ) {
        return null;
    }

    @Override
    public void onCreate() {
    	System.out.println("Service created");
    	c = this;
    
    	records = ReferenceApplication.records;
    	System.out.println("SIZE : "+ Integer.toString(records.size()));
    	
    	
    	// ANDROID BEACON LIBRARY
    	mBeaconManager = BeaconManager.getInstanceForApplication(this);
    	
    	// NORDIC BEACON
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        mBeaconManager.bind(this);

//    	final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor( 1 ); // Number of threads keep in the pool
//
//        executor.scheduleAtFixedRate( new Runnable() {
//
//            @Override
//            public void run() {
//            	Message msg = new Message();
//            	msg.obj="RUNNING";
//            	mRedToast.sendMessage(msg);
//            	System.out.println("RUNNING");
//            }
//        }, 1, 3000, TimeUnit.MILLISECONDS );
//    	
//        super.onCreate();
    }
    
    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        if (beacons.size() > 0) {
            for (Beacon beacon: beacons) {
            	System.out.println("> Beacon "+beacon.toString()+" is about "+beacon.getDistance()+" meters away, with Rssi: "+beacon.getRssi());            	
            }
        }
    }

    @Override
    public int onStartCommand( final Intent intent, final int flags, final int startId ) {
        return Service.START_STICKY;
    }
    
    /* Normal Toast */
    final static Handler mAdmin = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
           String mString=(String)msg.obj;
           Toast.makeText(c, mString, Toast.LENGTH_SHORT).show();
        }
    };
    
    /* Red Toast */
    final static Handler mRedToast = new Handler() { 
        @Override 
        public void handleMessage(Message msg) { 
           String mString=(String)msg.obj;
           
           Toast toast = Toast.makeText(c, mString, Toast.LENGTH_SHORT);
           TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
           v.setTextColor(Color.RED);
           toast.show();
        }
    };

	@Override
	public void onBeaconServiceConnect() {
		mAllBeaconsRegion = new Region("All beacons", null, null, null);
      try {
			mBeaconManager.startRangingBeaconsInRegion(mAllBeaconsRegion);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      mBeaconManager.setRangeNotifier(this);
		
	}
    
    
    /* Restart the service when the app is killed */
    
//    @SuppressLint("NewApi")
//	@Override
//    public void onTaskRemoved(Intent rootIntent){
////    	Log.d(TAG, "on Task Removed");
//        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
//        restartServiceIntent.setPackage(getPackageName());
//
//        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        alarmService.set(
//        AlarmManager.ELAPSED_REALTIME,
//        SystemClock.elapsedRealtime() + 1000,
//        restartServicePendingIntent);
//
//        super.onTaskRemoved(rootIntent);
//     }

	
}