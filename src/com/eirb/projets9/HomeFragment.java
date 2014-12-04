package com.eirb.projets9;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eirb.projets9.objects.Conference;
import com.eirb.projets9.objects.Session;
import com.eirb.projets9.objects.Talk;
import com.eirb.projets9.objects.Track;

public class HomeFragment extends Fragment {
	
	private ArrayList<Conference> confList;
	private ArrayList<Track> trackList;
	private ArrayList<Session> sessionList;
	private ArrayList<Talk> talkList;
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        String json = ReferenceApplication.readFromFile(ReferenceApplication.conferenceFilePath);
        System.out.println(isJSONValid(json));
        
        confList = new ArrayList<Conference>();
        
        try {
			JSONObject obj = new JSONObject(json);
			JSONArray conferences = obj.getJSONArray("Conference");

			for (int i = 0 ; i < conferences.length() ; i++){
				JSONObject conf = conferences.getJSONObject(i); 
				Conference conference = new Conference();
				
				conference.setId(Integer.parseInt(conf.getString("id")));
				conference.setAddress(conf.getString("address"));
				conference.setTitle(conf.getString("title"));
				conference.setStartDay(conf.getString("start_day"));
				conference.setEndDay(conf.getString("end_day"));
				conference.setMajor(conf.getString("major"));
				conference.setCreatedAt(Long.parseLong(conf.getString("created_at")));
				conference.setUpdatedAt(Long.parseLong(conf.getString("updated_at")));
				
				JSONArray tracks = conf.getJSONArray("tracks");
				for (int j = 0 ; j<tracks.length();j++){
					trackList = new ArrayList<Track>();
			        
					JSONObject tra = tracks.getJSONObject(j);
					Track track = new Track();
					track.setId(Integer.parseInt(tra.getString("id")));
					track.setTitle(tra.getString("title"));
					
					JSONArray sessions = tra.getJSONArray("sessions");
					for (int k = 0 ; k < sessions.length();k++){
						sessionList = new ArrayList<Session>();
						
						JSONObject ses = sessions.getJSONObject(k);
						Session session = new Session();
						session.setId(Integer.parseInt(ses.getString("id")));
						session.setStartTs(Long.parseLong(ses.getString("start_ts")));
						session.setStartTs(Long.parseLong(ses.getString("end_ts")));
						
						JSONArray talks = ses.getJSONArray("talks");
						for (int l = 0 ; l < talks.length();l++){
							talkList = new ArrayList<Talk>();
							
							JSONObject tal = talks.getJSONObject(l);
							Talk talk = new Talk();
							talk.setId(Integer.parseInt(tal.getString("id")));
							
							talkList.add(talk);
						}
						session.setList(talkList);
						sessionList.add(session);
					}
					
					track.setList(sessionList);
					trackList.add(track);
				}
				
				conference.setList(trackList);
				System.out.println(conference);
				confList.add(conference);
			}
//			System.out.println(confList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return rootView;
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
}
