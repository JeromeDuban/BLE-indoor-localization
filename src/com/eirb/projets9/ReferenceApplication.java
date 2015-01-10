package com.eirb.projets9;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Application;
import android.graphics.Typeface;

import com.eirb.projets9.objects.Building;
import com.eirb.projets9.objects.Conference;
import com.eirb.projets9.objects.Coordinate;
import com.eirb.projets9.objects.MapBeacon;
import com.eirb.projets9.scanner.BeaconRecord;
import com.eirb.projets9.scanner.NotificationService;

public class ReferenceApplication extends Application {

//	public BackgroundPowerSaver mBackgroundPowerSaver;	// Seems to kill the background service if the screen is off
	
	
	/* VARIABLES */
	
	// Time before being notified
	public static final int TIME_TO_BE_NOTIFIED = 10;
	// Maximum distance to be notified
	public static final double DISTANCE_TO_BE_NOTIFIED = 2;
	
	// Display records in logcat ?
	public static final boolean displayRecords = false;
	
	// Beacons records
	public static ArrayList<BeaconRecord> records;
	public static ArrayList<MapBeacon> mapBeacons;
	
	
	// Notification service, used for callbacks
	public static NotificationService notificationService;
	
	// Conference File Path
	public static String conferenceFile;
	public static String buildingFile;
	
	public static long lastTimestamp = 0;
	
	public static double lastX = -1;
	public static double lastY = -1;
	
	
	/* FONTS */
	public static Typeface fontMedium;
	public static Typeface fontThin;
	public static Typeface fontLight;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("onCreate Application");
//		mBackgroundPowerSaver = new BackgroundPowerSaver(this);
		
		
		records = new ArrayList<BeaconRecord>();
		mapBeacons = new ArrayList<MapBeacon>();
		conferenceFile = getFilesDir().getAbsolutePath().concat("/conference");
		buildingFile = getFilesDir().getAbsolutePath().concat("/building");

		// Fonts
		fontMedium = Typeface.createFromAsset(getAssets(), "HelveticaNeueLTStd-Md.otf");
		fontThin = Typeface.createFromAsset(getAssets(), "HelveticaNeueLTStd-Th.otf");
		fontLight = Typeface.createFromAsset(getAssets(), "HelveticaNeueLTStd-Lt.otf");
		
		// TODO : To be removed
//		mapBeacons.add(new MapBeacon("01122334-4556-6778-899a-abbccddeeff0","1","238",-1,-1,new Coordinate(120, 660)));
//		mapBeacons.add(new MapBeacon("e2c56db5-dffb-48d2-b060-d0f5a71096e0","1","232",-1,-1,new Coordinate(980, 660)));
//		mapBeacons.add(new MapBeacon("01122334-4556-6778-899a-abbccddeeff0","1","42",-1,-1,new Coordinate(980, 1300)));
		
		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","10","238",-1,-1,new Coordinate(120, 660)));
		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","10","232",-1,-1,new Coordinate(980, 660)));
		mapBeacons.add(new MapBeacon("3d4f13b4-d1fd-4049-80e5-d3edcc840b6a","10","42",-1,-1,new Coordinate(980, 1300)));
		
		
		
	};	
	
	/* Notification callback */
	public static void setNotificationService(NotificationService service){
		notificationService = service;
	}
	/* Record callback */
	public static void recordAdded(){
//		notificationService.recordCallback();
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

	
	 
	 /* ------ OLD METHODS ---------- */
	 
//		public static void writeToFile(String content, String path) {
//	 
//	FileOutputStream fop = null;
//	File file;
//
//	try {
//
//		file = new File(path);
//		fop = new FileOutputStream(file);
//
//		// if file doesn't exists, then create it
//		if (!file.exists()) {
//			file.createNewFile();
//		}
//
//		// get the content in bytes
//		byte[] contentInBytes = content.getBytes();
//
//		fop.write(contentInBytes);
//		fop.flush();
//		fop.close();
//
//	} catch (IOException e) {
//		e.printStackTrace();
//	} finally {
//		try {
//			if (fop != null) {
//				fop.close();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}

//public static String readFromFile(String path) {
//	 
//	BufferedReader br = null;
//	String file = "";
//
//	try {
//
//		String sCurrentLine;
//
//		br = new BufferedReader(new FileReader(path));
//
//		while ((sCurrentLine = br.readLine()) != null) {
//			file = file.concat(sCurrentLine);
//		}
//
//	} catch (IOException e) {
//		e.printStackTrace();
//	} finally {
//		try {
//			if (br != null)br.close();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//	}
//	return file;
//}
	 
	 
	
}
