package com.eirb.projets9.scanner;

import java.util.ArrayList;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.eirb.projets9.MainActivity;
import com.eirb.projets9.R;
import com.eirb.projets9.ReferenceApplication;

public class NotificationService extends Service{
	
	private static Context c;
	
	
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
//    	System.out.println("CALLBACK");
//    	System.out.println(records.toString());
    	for (int i = 0; i < records.size() ; i++ ){
    		BeaconRecord br = records.get(i);
    		
    		int j = br.getList().size()-1 ;
    		boolean notif = false;
    		
    		while (j>=0 && (new Date().getTime() - br.getList().get(j).getTimestamp()) < ReferenceApplication.TIME_TO_BE_NOTIFIED * 1000 ){
    			 if( br.getList().get(j).getDistance() < ReferenceApplication.DISTANCE_TO_BE_NOTIFIED && !br.isNotified()){
    				 System.out.println(j);
    				 System.out.println(br.getList().get(j).getDistance());
    				 notif = true;
    			 }
    				 
    			 else{
    				notif = false;
    				break;
    			 }
    			j--;
    		}
    		if(j >= 0 && notif && br.getList().get(j).getDistance() < ReferenceApplication.DISTANCE_TO_BE_NOTIFIED && (new Date().getTime() - br.getList().get(j).getTimestamp()) >= ReferenceApplication.TIME_TO_BE_NOTIFIED * 1000){
    			System.out.println("MORE THAN 10 seconds");
    			generateNotification(c, br.getUuid()+"|"+br.getMajor()+"|"+br.getMinor());
    			br.setNotified(true);
    		}
    		
    		
    		
    	}
    }
    
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
         
        String delims = "\\|";
        String[] tokens = message.split(delims);
         
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(tokens[0])
                .setContentText("major : " + tokens[1] +" // minor : " + tokens[2])
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("major : " + tokens[1]+ "\nminor : " + tokens[2]))
                ;
         
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra("goTo", "notif");
         
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
         
        mBuilder.setContentIntent(resultPendingIntent);
         
        NotificationManager mNotifyMgr = 
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(0, mBuilder.build());
 
    }
    
    
	@Override
    public int onStartCommand( final Intent intent, final int flags, final int startId ) {
        return Service.START_STICKY;
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