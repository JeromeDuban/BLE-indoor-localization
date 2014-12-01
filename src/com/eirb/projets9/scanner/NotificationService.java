package com.eirb.projets9.scanner;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.eirb.projets9.ReferenceApplication;

public class NotificationService extends Service{
	
	static Context c;
	
	public ArrayList<BeaconRecord> records;
		
    @Override
    public IBinder onBind( final Intent arg0 ) {
        return null;
    }

    @Override
    public void onCreate() {
    	System.out.println("Notification Service created");
    	c = this;
    	
    	records = ReferenceApplication.records;
    	ReferenceApplication.setNotificationService(this);
        		
        super.onCreate();
    }
       
    public void recordCallback(){
    	System.out.println("CALLBACK");
    }
    
    
	@Override
    public int onStartCommand( final Intent intent, final int flags, final int startId ) {
        return Service.START_STICKY;
    }
    
    /* Normal Toast */
    final static Handler mToast = new Handler() { 
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