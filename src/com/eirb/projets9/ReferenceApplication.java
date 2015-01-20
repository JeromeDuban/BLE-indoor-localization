package com.eirb.projets9;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Application;
import android.graphics.Typeface;

import com.eirb.projets9.objects.Beacon;
import com.eirb.projets9.objects.Building;
import com.eirb.projets9.objects.Conference;
import com.eirb.projets9.objects.Coordinate;
import com.eirb.projets9.objects.Floor;
import com.eirb.projets9.objects.MapBeacon;
import com.eirb.projets9.objects.Notification;
import com.eirb.projets9.objects.Room;
import com.eirb.projets9.objects.Session;
import com.eirb.projets9.objects.Talk;
import com.eirb.projets9.objects.Track;
import com.eirb.projets9.scanner.BeaconRecord;
import com.eirb.projets9.scanner.NotificationService;

public class ReferenceApplication extends Application {

//	public BackgroundPowerSaver mBackgroundPowerSaver;	// Seems to kill the background service if the screen is off
	
	
	/* VARIABLES */
	
	// Time before being notified
	public static final int TIME_TO_BE_NOTIFIED = ;
	// Maximum distance to be notified
	public static final double DISTANCE_TO_BE_NOTIFIED = 2;
	
	// Display records in logcat ?
	public static boolean displayRecords = false;
	
	// Beacons records
	public static ArrayList<BeaconRecord> records;
	public static ArrayList<MapBeacon> mapBeacons;
	
	
	// Notification service, used for callbacks
	public static NotificationService notificationService;
	public static ArrayList<Notification> notificationList;
	
	
	// Conference File Path
	public static String conferenceFile;
	public static String buildingFile;
	public static String beaconsFile;
	public static String notificationsFile;
	
	public static long lastTimestamp = 0;
	
	public static double lastX = -1;
	public static double lastY = -1;
	public static int lastNumberofBeacons = 0;
	
	
	/* FONTS */
	public static Typeface fontMedium;
	public static Typeface fontThin;
	public static Typeface fontLight;
	
	
	public static MainActivity mainActivity;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("onCreate Application");
//		mBackgroundPowerSaver = new BackgroundPowerSaver(this);
		
		
		records = new ArrayList<BeaconRecord>();
		mapBeacons = new ArrayList<MapBeacon>();
		conferenceFile = getFilesDir().getAbsolutePath().concat("/conference");
		buildingFile = getFilesDir().getAbsolutePath().concat("/building");
		beaconsFile = getFilesDir().getAbsolutePath().concat("/beacons");
		notificationsFile = getFilesDir().getAbsolutePath().concat("/notifications");

		// Fonts
		fontMedium = Typeface.createFromAsset(getAssets(), "HelveticaNeueLTStd-Md.otf");
		fontThin = Typeface.createFromAsset(getAssets(), "HelveticaNeueLTStd-Th.otf");
		fontLight = Typeface.createFromAsset(getAssets(), "HelveticaNeueLTStd-Lt.otf");
		
		if(new File(notificationsFile).exists()){
			notificationList = deserializeNotifications();
		}
		
		// TODO : To be removed
		
//		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","61298","238",-1,-1,new Coordinate(120, 660)));
//		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","61298","232",-1,-1,new Coordinate(980, 660)));
//		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","61298","42",-1,-1,new Coordinate(980, 1300)));
		
		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","61298","11",-1,-1,new Coordinate(835, 445))); //915
		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","61298","12",-1,-1,new Coordinate(913, 445))); //990
		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","61298","42",-1,-1,new Coordinate(913, 485)));
		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","61298","232",-1,-1,new Coordinate(835, 485)));
		
	};	
	
	/* Notification callback */
	public static void setNotificationService(NotificationService service){
		notificationService = service;
	}
	/* Record callback */
	public static void recordAdded(){
		notificationService.recordCallback();
	}
		
	/* Save object to file */
	public static void serializeConference(Conference conf){
		 
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
	
	public static void serializeBuilding(Building build) {
		try{
			 
			FileOutputStream fout = new FileOutputStream(buildingFile);
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(build);
			oos.close();
			System.out.println("Done");
	 
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void serializeBeaconList(
			ArrayList<com.eirb.projets9.objects.Beacon> beaconList) {
		try{
			 
			FileOutputStream fout = new FileOutputStream(beaconsFile);
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(beaconList);
			oos.close();
			System.out.println("Done");
	 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public static void serializeNotifications() {
		
		try{
			 
			FileOutputStream fout = new FileOutputStream(notificationsFile);
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(notificationList);
			oos.close();
			System.out.println("Done");
	 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	/* Get object from file */
	 public static Conference deserializeConference(){
		 
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
	 
	 public static Building deserializeBuilding(){
		 
		   Building build; 
		   try{
	 
			   FileInputStream fin = new FileInputStream(buildingFile);
			   ObjectInputStream ois = new ObjectInputStream(fin);
			   build = (Building) ois.readObject();
			   ois.close();
	 
			   return build;
	 
		   }catch(Exception ex){
			   ex.printStackTrace();
			   return null;
		   } 
	   }
	 
	public static ArrayList<com.eirb.projets9.objects.Beacon> deserializeBeacons() {

		ArrayList<com.eirb.projets9.objects.Beacon> beacons;
		try {

			FileInputStream fin = new FileInputStream(beaconsFile);
			ObjectInputStream ois = new ObjectInputStream(fin);
			beacons = (ArrayList<Beacon>) ois.readObject();
			ois.close();

			return beacons;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private ArrayList<Notification> deserializeNotifications() {
		ArrayList<Notification> notifs;
		try {

			FileInputStream fin = new FileInputStream(notificationsFile);
			ObjectInputStream ois = new ObjectInputStream(fin);
			notifs = (ArrayList<Notification>) ois.readObject();
			ois.close();

			return notifs;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	 
	 
	 /* Get Coordinates of a beacon for a given uuid - major - minor */
	 public static Coordinate getCoordinate(String uuid, String major, String minor){
		 for (int i = 0 ; i < mapBeacons.size() ; i++){
			 MapBeacon mapBeacon = mapBeacons.get(i);
			 if (mapBeacon.getUuid().equals(uuid) && mapBeacon.getMajor().equals(major) && mapBeacon.getMinor().equals(minor)){
				 return mapBeacon.getCoordinate();
			 }
		 }
		 return new Coordinate(-1, -1);
	 }
	 
	 public static boolean isInMapBeacon(BeaconRecord br){
		 for (int i = 0; i < mapBeacons.size() ; i++){
			 MapBeacon mb = mapBeacons.get(i);
			 if (br.getUuid().equals(mb.getUuid()))
				 if(br.getMajor().equals(mb.getMajor()))
					 if(br.getMinor().equals(mb.getMinor()))
						 return true;
		 }
		 
		 return false;
	 }

	 public static String getRoomName(Building building, int id ){
		 
		 ArrayList<Floor> fl = building.getList();
		 for (int i = 0 ; i < fl.size();i++){
			 
			 ArrayList<Room> rl = fl.get(i).getList();
			 for (int j = 0 ; j< rl.size();j++){
				 
				 if (rl.get(j).getId() == id)
					 return rl.get(j).getName();
			 }
		 }
		 return null;
	 }
	 
	 public static Talk getNextTalk(int roomID){
		 
		 Conference c = deserializeConference();
		 if (c == null)
			 return null;
		 
		 for (int i = 0; i < c.getList().size() ; i++){
			 Track track = c.getList().get(i);
			 for (int j = 0 ; j < track.getList().size() ; j++){
				 Session session = track.getList().get(j);
				 
				 if(session.getRoom_id() == roomID){
					 ArrayList<Talk> list = session.getList();
					 Collections.sort(list);
					 if(list.size() > 0){
						 return list.get(0);
					 }
				 }
			 }
		 }
	
		 return null;
	 }
}
