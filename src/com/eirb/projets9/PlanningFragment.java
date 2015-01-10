package com.eirb.projets9;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eirb.projets9.objects.Building;
import com.eirb.projets9.objects.Conference;
import com.eirb.projets9.objects.Session;
import com.eirb.projets9.objects.Talk;
import com.eirb.projets9.objects.Track;

public class PlanningFragment extends Fragment {
	
	public PlanningFragment(){}
	
    LinearLayout listPlanning;
//    DateFormat df = new SimpleDateFormat("hh:'00' a");
    
    DateFormat df = new SimpleDateFormat("HH:'00'", Locale.US);
    DateFormat d = new SimpleDateFormat("EEE, d MMM", Locale.US);
    
    ArrayList<String> rooms;
    Building building = null;
    
    Date previous = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_planning, container, false);
        
        File file = new File(ReferenceApplication.buildingFile);
   	 
		if(file.exists()){
			building = ReferenceApplication.deserializeBuilding();
		}
        
        rooms = new ArrayList<String>();
        ArrayList<Talk> l = getAllTalks();
        Collections.sort(l);
        
        listPlanning= (LinearLayout) rootView.findViewById(R.id.listSchedule);
        
        /* Display */
        for (int i = 0; i < l.size(); i++) {

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.planning_element,listPlanning, false);
            
            TextView start = (TextView) view.findViewById(R.id.start);
            TextView end = (TextView) view.findViewById(R.id.end);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
            
            Talk t = l.get(i);
            
            /* Date */
            
            Date s = new Date(t.getStartTs() * 1000);
            Date e = new Date(t.getEndTs() * 1000);
                        
            if(previous == null){
            	previous = s;
            	View date = inflater.inflate(R.layout.date,listPlanning, false);
            	TextView dateTxt = (TextView) date.findViewById(R.id.tv);
            	dateTxt.setText(d.format(s));
            	listPlanning.addView(date);
            }
            	
            if(previous == null || (s.getYear() == previous.getYear() && s.getMonth() == previous.getMonth() && s.getDay() == previous.getDay())){
            }
            else{
            	View date = inflater.inflate(R.layout.date,listPlanning, false);
            	TextView dateTxt = (TextView) date.findViewById(R.id.tv);
            	dateTxt.setText(d.format(s));
            	listPlanning.addView(date);
            }
            previous = s;
            
            
            /* Text */
            title.setText(t.getTitle());
            title.setText( d.format(s));
            if (building != null){
            	String sub;
				if ((sub = ReferenceApplication.getRoomName(building, Integer.parseInt(rooms.get(i)))) != null){
            		subtitle.setText("Room " + sub);
            	}
				else
					subtitle.setText("Room " + rooms.get(i));
            }
            else
            	subtitle.setText("");
            
            start.setText(df.format(s));
            end.setText(df.format(e));
            
            listPlanning.addView(view);

            
        }
        
        return rootView;
    }
	
	private ArrayList<Talk> getAllTalks(){
		ArrayList<Talk> l = new ArrayList<Talk>();
		
		File file = new File(ReferenceApplication.conferenceFile);
        if (file.exists()) {
        	Conference c = ReferenceApplication.deserializeConference();
    		ArrayList<Track> cl = c.getList();
    		for (int i = 0; i < cl.size() ; i++){
    			
    			ArrayList<Session> cs = cl.get(i).getList();
    			for (int j = 0; j < cs.size() ; j++){
    				
    				ArrayList<Talk> ct = cs.get(j).getList();
    				for (int k = 0; k < ct.size() ; k++){
    					
    					l.add(ct.get(k));
    					rooms.add(Integer.toString((cs.get(j).getRoom_id())));
    				}
    				
    			}
    		}
    		
		}
		return l;
	}
}
