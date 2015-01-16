package com.eirb.projets9.scanner;

import java.util.ArrayList;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.eirb.projets9.MainActivity;
import com.eirb.projets9.R;
import com.eirb.projets9.ReferenceApplication;
import com.eirb.projets9.objects.Beacon;
import com.eirb.projets9.objects.Talk;

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
    	
    	ArrayList<Beacon> beaconsList = ReferenceApplication.deserializeBeacons();
    	
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
    			
    			/* GET DATA */
    			if (beaconsList != null){
    				for (int k = 0; k < beaconsList.size() ;k++){
        				if (beaconsList.get(k).getUuid().toLowerCase().equals(br.getUuid().toLowerCase()))
        					if (beaconsList.get(k).getMajor() == Integer.parseInt(br.getMajor()))
        						if (beaconsList.get(k).getMinor() == Integer.parseInt(br.getMinor())){
        							System.out.println("NOTIF");
        							generateNotification(c, beaconsList.get(k).getRoom_id(), br);
        						}
        							
        			}	
    			}
    			
//    			generateNotification(c, br.getUuid()+"|"+br.getMajor()+"|"+br.getMinor());
//    			br.setNotified(true);
    		}
    		
    		
    		
    	}
    }
    
    private static void generateNotification(Context context, int roomID, BeaconRecord br) {
        int icon = R.drawable.ic_launcher;
         
//        String delims = "\\|";
//        String[] tokens = i.split(delims);
        
        Talk talk = ReferenceApplication.getNextTalk(roomID);
        
        String roomName = "ERROR";
        if (ReferenceApplication.deserializeBuilding() != null){
        	roomName = ReferenceApplication.getRoomName(ReferenceApplication.deserializeBuilding(), roomID);
        }
         
        
        if (talk != null && roomName != null){
        	NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                    .setSmallIcon(icon)
                    .setContentTitle("Coming : " + talk.getTitle())
                    .setContentText("Room " + roomName )
                    .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(talk.getConfAbstract()));
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
            mNotifyMgr.notify(Integer.parseInt(br.getMinor()), mBuilder.build());
            
            
            if(ReferenceApplication.notificationList == null)
            	ReferenceApplication.notificationList = new ArrayList<com.eirb.projets9.objects.Notification>();
            
            ReferenceApplication.notificationList.add(0, new com.eirb.projets9.objects.Notification(new Date().getTime(), talk,roomName));
            ReferenceApplication.serializeNotifications();
            
            br.setNotified(true);
        }
        else{
        	System.out.println("TRUC NULL");
        }
        
 
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