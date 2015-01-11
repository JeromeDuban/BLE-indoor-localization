package com.eirb.projets9.scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.eirb.projets9.ReferenceApplication;
import com.eirb.projets9.objects.Building;
import com.eirb.projets9.objects.Conference;
import com.eirb.projets9.objects.Coordinate;
import com.eirb.projets9.objects.Floor;
import com.eirb.projets9.objects.Room;
import com.eirb.projets9.objects.Session;
import com.eirb.projets9.objects.Talk;
import com.eirb.projets9.objects.Track;

public class RangingService extends Service implements BeaconConsumer, RangeNotifier{
	
	static Context c;
	
	private BeaconManager mBeaconManager;
	private Region mAllBeaconsRegion;
	
	public ArrayList<BeaconRecord> records;
	
	private Conference confToSave;
	private ArrayList<Track> trackList;
	private ArrayList<Session> sessionList;
	private ArrayList<Talk> talkList;
	

	private Building buildToSave;
	private ArrayList<Floor> floorList;
	private ArrayList<Room> roomList;
	
	private ArrayList<com.eirb.projets9.objects.Beacon> beaconList;
	
	@SuppressWarnings("unused")	private BackgroundPowerSaver mBackgroundPowerSaver;
	@SuppressWarnings("unused")	private RegionBootstrap mRegionBootstrap;
	
    @Override
    public IBinder onBind( final Intent arg0 ) {
        return null;
    }

    @Override
    public void onCreate() {
    	System.out.println("Ranging Service created");
    	c = this;
    
    	records = ReferenceApplication.records;
//    	System.out.println("SIZE : "+ Integer.toString(records.size()));
    	
    	
    	// ANDROID BEACON LIBRARY
    	mBeaconManager = BeaconManager.getInstanceForApplication(this);
    	
    	// NORDIC BEACON
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        mBeaconManager.bind(this);

    }
    
	@Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        if (beacons.size() > 0) {

        	// We need the same timestamp for all records 
        	long timestamp = new Date().getTime();
        	
            for (Beacon beacon: beacons) {           
            	ScanRecord sr = new ScanRecord(beacon.getDistance(), beacon.getRssi(), timestamp);
                BeaconRecord br = new BeaconRecord(beacon.getId1().toString(),beacon.getId2().toString(),beacon.getId3().toString(), sr);
                
                if (isNew(br)){ // Add new beacon to list
                	records.add(br);
                	System.out.println("New Beacon added");
                	System.out.println(br);
                	
                	// Need to create conference.json ?
                	
                	File file = new File(ReferenceApplication.conferenceFile);
            		if (!file.exists()) {
            			System.out.println("Download started");

            			downloadBuilding("https://gist.githubusercontent.com/frco9/3670e5e353aadea2c417/raw/7ae27e0ac33388cfbc35107e802feeaf51535c18/Topology_10.json",beacon.getId2().toInt());
            			downloadConference("https://gist.githubusercontent.com/jejefcgb/04bd319ac8dc428911c1/raw/Conference_10.json", beacon.getId2().toInt());
            			downloadBeacon("https://gist.githubusercontent.com/jejefcgb/31e6dcd7ee2b98cc06d5/raw/", beacon.getId2().toInt());
//            			downloadConference("https://gist.githubusercontent.com/frco9/95a6ef89c7d4d4e72c82/raw/cc1684e795566c08103ce87b7841715a45aa5679/Conference_10.json", beacon.getId2().toInt());
            		}
                }
                else{
                	addScanRecord(br,sr);
                	if (ReferenceApplication.displayRecords == true)
                		System.out.println(br);
                	ReferenceApplication.lastTimestamp = timestamp;
                	ReferenceApplication.recordAdded();
                }
                	
                 
            }
        }
    }

    private void addScanRecord(BeaconRecord br, ScanRecord sr) {
    	for (int i=0; i<records.size();i++){
			if (records.get(i).equals(br)){
				records.get(i).addToList(sr);
			}
		}
	}
    
    public boolean isNew( BeaconRecord br){
		for (int i=0; i<records.size();i++){
			if (records.get(i).equals(br)){
				return false;
			}
		}
		return true;
	}
    
    
    /*
     * SERVICE MANAGEMENT
     */
    

	@Override
    public int onStartCommand( final Intent intent, final int flags, final int startId ) {
        return Service.START_STICKY;
    }
    
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

		
	public boolean isJSONValid(String test) {
	    try {
	        new JSONObject(test);
	    } catch (JSONException ex) {
	        // edited, to include @Arthur's comment
	        // e.g. in case JSONArray is valid as well...
	        try {
	            new JSONArray(test);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
	/* Download conference data from a server according to its major */
	
	public void downloadConference(String url, int major){
		if (isOnline()) {
			StringBuilder response = new StringBuilder();
			
			// GET file from server
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse execute;
			InputStream content;
			try {
				execute = client.execute(httpGet);
				content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response.append(s);
					response.append("\n");
				}
			} catch (ClientProtocolException e) {
				System.out.println(e);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//Check answer
			System.out.println(response.toString());
			
			// Parse json
			if (!response.toString().equals("")) {
				System.out.println("Writing conference file");

				String json = response.toString();
				
				System.out.println("Valid json : " + isJSONValid(json));

				confToSave = null;

				try {
					JSONObject obj = new JSONObject(json);
//					JSONArray conferences = obj.getJSONArray("Conference");
//					
//					// Parse conference
//					for (int i = 0; i < conferences.length(); i++) {
//
//						JSONObject conf = conferences.getJSONObject(i);
					JSONObject conf = obj;
					
					System.out.println(conf.getString("major") +":"+ Integer.toString(major));
					
					if (Integer.parseInt(conf.getString("major")) == major) {
						Conference conference = new Conference();

						conference.setId(Integer.parseInt(conf
								.getString("id")));
						conference.setAddress(conf.getString("address"));
						conference.setTitle(conf.getString("title"));
						conference.setStartDay(conf.getString("start_day"));
						conference.setEndDay(conf.getString("end_day"));
						conference.setMajor(conf.getString("major"));
						conference.setCreatedAt(Long.parseLong(conf
								.getString("created_at")));
						conference.setUpdatedAt(Long.parseLong(conf
								.getString("updated_at")));

						JSONArray tracks = conf.getJSONArray("tracks");
						
						// Parse tracks
						trackList = new ArrayList<Track>();
						for (int j = 0; j < tracks.length(); j++) {

							JSONObject tra = tracks.getJSONObject(j);
							Track track = new Track();
							track.setId(Integer.parseInt(tra
									.getString("id")));
							track.setTitle(tra.getString("title"));

							JSONArray sessions = tra
									.getJSONArray("sessions");
							
							// Parse sessions
							sessionList = new ArrayList<Session>();
							for (int k = 0; k < sessions.length(); k++) {

								JSONObject ses = sessions.getJSONObject(k);
								Session session = new Session();
								session.setId(Integer.parseInt(ses.getString("id")));
								session.setStartTs(Long.parseLong(ses.getString("start_ts")));
								session.setStartTs(Long.parseLong(ses.getString("end_ts")));
								session.setRoom_id(Integer.parseInt(ses.getString("room_id")));

								JSONArray talks = ses.getJSONArray("talks");
								
								// Parse talks
								talkList = new ArrayList<Talk>();
								for (int l = 0; l < talks.length(); l++) {

									JSONObject tal = talks.getJSONObject(l);
									Talk talk = new Talk();
									talk.setId(Integer.parseInt(tal.getString("id")));
									talk.setTitle(tal.getString("title"));
									talk.setStartTs(Long.parseLong(tal.getString("start_ts")));
									talk.setEndTs(Long.parseLong(tal.getString("end_ts")));
									talk.setSpeaker(tal.getString("speaker"));
									talk.setConfAbstract(tal.getString("abstract"));
									talk.setBody(tal.getString("abstract"));

									talkList.add(talk);
								}
								session.setList(talkList);
								sessionList.add(session);
								
							}

							track.setList(sessionList);
							trackList.add(track);

						}
						conference.setList(trackList);
						confToSave = conference;
					}

//					}
					// Save conference object to file
					if(confToSave != null){
						System.out.println(confToSave);
						ReferenceApplication.serializeConference(confToSave);
					}
					else
						System.out.println("confToSave == null");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		} else {
			System.out.println("Not online");
		}
		
	}
	
	public void downloadBuilding(String url, int major){
		if (isOnline()) {
			StringBuilder response = new StringBuilder();
			
			// GET file from server
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse execute;
			InputStream content;
			try {
				execute = client.execute(httpGet);
				content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response.append(s);
					response.append("\n");
				}
			} catch (ClientProtocolException e) {
				System.out.println(e);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//Check answer
			System.out.println(response.toString());
			
			// Parse json
			if (!response.toString().equals("")) {
				System.out.println("Writing building file");

				String json = response.toString();
				
				System.out.println("Valid json : " + isJSONValid(json));

				buildToSave = null;

				try {
					JSONObject build = new JSONObject(json);
					
					System.out.println(build.getString("major") +":"+ Integer.toString(major));
					
					if (Integer.parseInt(build.getString("major")) == major) {
						Building building = new Building();
						building.setMajor(Integer.parseInt(build.getString("major")));
						building.setName(build.getString("name"));
						building.setLink(build.getString("link"));
						 
						floorList = new ArrayList<Floor>();
						JSONArray floorsArray = build.getJSONArray("floors");
						
						for (int i = 0 ; i < floorsArray.length() ; i++){
							
							JSONObject flo = floorsArray.getJSONObject(i);
							Floor floor = new Floor();
							floor.setId(Integer.parseInt(flo.getString("id")));
							floor.setName(flo.getString("name"));
							
							roomList = new ArrayList<Room>();
							JSONArray roomsArray = flo.getJSONArray("rooms");
							
							for (int j = 0 ; j < roomsArray.length() ; j++){
								
								JSONObject roo = roomsArray.getJSONObject(j);
								Room room = new Room();
								room.setId(Integer.parseInt(roo.getString("id")));
								room.setDom_id(roo.getString("dom_id"));
								room.setName(roo.getString("name"));
								
								roomList.add(room);
							}
							floor.setList(roomList);
							floorList.add(floor);
						}
						building.setList(floorList);
						buildToSave = building;
					}

					// Save conference object to file
					if(buildToSave != null){
						System.out.println(buildToSave);
						ReferenceApplication.serializeBuilding(buildToSave);
					}
					else
						System.out.println("confToSave == null");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
		} else {
			System.out.println("Not online");
		}
		
	}
	
	
	public void downloadBeacon(String url, int major){
		if (isOnline()) {
			StringBuilder response = new StringBuilder();
			
			// GET file from server
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse execute;
			InputStream content;
			try {
				execute = client.execute(httpGet);
				content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response.append(s);
					response.append("\n");
				}
			} catch (ClientProtocolException e) {
				System.out.println(e);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//Check answer
			System.out.println(response.toString());
			
			// Parse json
			if (!response.toString().equals("")) {
				System.out.println("Writing beacon file");

				String json = response.toString();
				
				System.out.println("Valid json : " + isJSONValid(json));

				beaconList = null;
				beaconList = new ArrayList<com.eirb.projets9.objects.Beacon>();
				
				try {
					JSONArray beacons = new JSONArray(json);
					for (int i = 0 ; i < beacons.length() ; i++){
						JSONObject beacon = beacons.getJSONObject(i);
						com.eirb.projets9.objects.Beacon b = new com.eirb.projets9.objects.Beacon();
						
						b.setId(Integer.parseInt(beacon.getString("id")));
						b.setUuid(beacon.getString("uuid"));
						b.setMajor(Integer.parseInt(beacon.getString("major")));
						b.setMinor(Integer.parseInt(beacon.getString("minor")));
						b.setFloor_id(Integer.parseInt(beacon.getString("floor_id")));
						b.setRoom_id(Integer.parseInt(beacon.getString("room_id")));
						
						JSONObject coordinates = beacon.getJSONObject("coordinates");
						b.setCoordinates(new Coordinate(Integer.parseInt(coordinates.getString("x")), Integer.parseInt(coordinates.getString("y"))));

						beaconList.add(b);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(beaconList != null){
					System.out.println(beaconList);
					ReferenceApplication.serializeBeaconList(beaconList);
				}
				else
					System.out.println("confToSave == null");
					
					
			}
			
		} else {
			System.out.println("Not online");
		}
		
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
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