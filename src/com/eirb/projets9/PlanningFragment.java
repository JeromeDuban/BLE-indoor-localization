package com.eirb.projets9;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eirb.projets9.objects.Building;
import com.eirb.projets9.objects.Conference;
import com.eirb.projets9.objects.PlanningElement;
import com.eirb.projets9.objects.Session;
import com.eirb.projets9.objects.Talk;
import com.eirb.projets9.objects.Track;

public class PlanningFragment extends Fragment {

	public PlanningFragment(){}
	
    LinearLayout listPlanning;
//    DateFormat df = new SimpleDateFormat("hh:'00' a");
    
    DateFormat df = new SimpleDateFormat("HH:mm", Locale.US);
    DateFormat d = new SimpleDateFormat("EEE, d MMM", Locale.US);
    
    Building building = null;
    
    Date previous = null;
    
    MainActivity a;
    
    SparseIntArray array = new SparseIntArray();
    
    
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_planning, container, false);
        
        a = (MainActivity) getActivity();
        
        File file = new File(ReferenceApplication.buildingFile);
   	 
		if(file.exists()){
			building = ReferenceApplication.deserializeBuilding();
		}
        
        ArrayList<PlanningElement> l = getAllTalks();
        Collections.sort(l);
        
        listPlanning= (LinearLayout) rootView.findViewById(R.id.listSchedule);
        
        /* Display */
        for (int i = 0; i < l.size(); i++) {

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.planning_element,listPlanning, false);
            
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.container);
            TextView start = (TextView) view.findViewById(R.id.start);
            TextView end = (TextView) view.findViewById(R.id.end);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
            View bar = view.findViewById(R.id.bar);

            int roomId = l.get(i).getId();
            if (array.get(roomId, -1) != -1){
            	bar.setBackgroundColor(array.get(roomId));
            }
            else{
            	Random rand = new Random();
                int randomColor = 0xff000000 + rand.nextInt(0xFFFFFF);
                System.out.println(randomColor);
            	array.append(roomId, randomColor);
            	bar.setBackgroundColor(randomColor);
            	bar.invalidate();
            }
            	
          
            final Talk t = l.get(i).getTalk();
            
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
            
            
            
            /* Title */
            title.setText(t.getTitle());
            
            /* Subtitle */
            String sub ="";
            if (building != null){
				if ((sub = ReferenceApplication.getRoomName(building, l.get(i).getId())) != null){
            		subtitle.setText("Room " + sub);
            	}
				else
					subtitle.setText("Room " + l.get(i).getId());
            }
            else
            	subtitle.setText("");
            
            start.setText(df.format(s));
            end.setText(df.format(e));
            
            final String subFinal= sub;
            final String sFinal= df.format(s);
            final String eFinal= df.format(e);
            
            layout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), Description.class);
					Bundle bundle = new Bundle();
					bundle.putString("Title", t.getTitle());
					bundle.putString("Subtitle",subFinal);
					bundle.putString("Start", sFinal);
					bundle.putString("End", eFinal);
					bundle.putString("Body", t.getBody());
					intent.putExtras(bundle);
					
					startActivityForResult(intent, 0);
				}
			});
            
            
            
            listPlanning.addView(view);
        }
        
        return rootView;
    }
	
	private ArrayList<PlanningElement> getAllTalks(){
		ArrayList<PlanningElement> l = new ArrayList<PlanningElement>();
		PlanningElement pe ;
		
		File file = new File(ReferenceApplication.conferenceFile);
        if (file.exists()) {
        	Conference c = ReferenceApplication.deserializeConference();
    		ArrayList<Track> cl = c.getList();
    		for (int i = 0; i < cl.size() ; i++){
    			
    			ArrayList<Session> cs = cl.get(i).getList();
    			for (int j = 0; j < cs.size() ; j++){
    				
    				ArrayList<Talk> ct = cs.get(j).getList();
    				for (int k = 0; k < ct.size() ; k++){
    					
    					pe = new PlanningElement();
    					pe.setTalk(ct.get(k));
    					pe.setId(cs.get(j).getRoom_id());
    					
    					l.add(pe);
    				}
    				
    			}
    		}
    		
		}
		return l;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) {
//			Toast.makeText(getActivity(), data.getStringExtra("room"), Toast.LENGTH_SHORT).show();
			a.switchMapFragment(data.getStringExtra("room"));
		}
	}
}
