package com.eirb.projets9;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eirb.projets9.objects.Building;
import com.eirb.projets9.objects.Conference;
import com.eirb.projets9.objects.PlanningElement;
import com.eirb.projets9.objects.Session;
import com.eirb.projets9.objects.Talk;
import com.eirb.projets9.objects.Track;

public class HomeFragment extends Fragment {
		
	public HomeFragment(){}
	
	private File file;
	private TextView title;
	private ListView list;
	private EditText input;
	private ArrayAdapter<String> adapter;
	private MainActivity a;
	private Building building;
	private LinearLayout listPlanning;
	private Date previous = null;
	private DateFormat df = new SimpleDateFormat("HH:mm", Locale.US);
	private DateFormat d = new SimpleDateFormat("EEE, d MMM", Locale.US);
	private TextView dates;
	private TextView addresse;
	private RelativeLayout confLayout;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        a = (MainActivity) getActivity();
        
        confLayout = (RelativeLayout) rootView.findViewById(R.id.conference);
        
        title = (TextView) rootView.findViewById(R.id.txtLabel);
        dates = (TextView) rootView.findViewById(R.id.dates);
        addresse = (TextView) rootView.findViewById(R.id.addresse);
        
        file = new File(ReferenceApplication.conferenceFile);
        if (file.exists()) {
        	confLayout.setVisibility(View.VISIBLE);
			updateView();
		}
        
        file = new File(ReferenceApplication.buildingFile);
      	 
		if(file.exists()){
			building = ReferenceApplication.deserializeBuilding();
		}

        handler.postDelayed(runnable, 100);
        
        /* MAP */
        
        list = (ListView) rootView.findViewById(R.id.searchResults);
	    input = (EditText) rootView.findViewById(R.id.editText);
	    list.setVisibility(View.GONE);
        
        final ArrayList<String> display = new ArrayList<String>();
	    display.add("I001");
	    display.add("I002");
	    display.add("I003");
	    display.add("I004");
	    display.add("Grand Amphithéâtre");
	    display.add("Sujet n°1");
	    
	    String[] array = new String[display.size()];
	    display.toArray(array);
	    
	    final ArrayList<String> action = new ArrayList<String>();
	    action.add("I001");
	    action.add("I002");
	    action.add("I003");
	    action.add("I004");
	    action.add("Grand Amphithéâtre");
	    action.add("Grand Amphithéâtre");
	    

	    adapter =  new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1 , array);
	    list.setAdapter(adapter);

	    // By using setTextFilterEnabled method in listview we can filter the listview items.

	     list.setTextFilterEnabled(true);
	     input.addTextChangedListener(new TextWatcher() {

	        @Override
	        public void onTextChanged( CharSequence arg0, int arg1, int arg2, int arg3){
	            // TODO Auto-generated method stub
	        	list.setVisibility(View.VISIBLE);
	        	if(arg3 == 0){
	        		list.setVisibility(View.GONE);
	        	}
	        		
	        }

	        @Override
	        public void beforeTextChanged( CharSequence arg0, int arg1, int arg2, int arg3) {

	        }

	        @Override
	        public void afterTextChanged( Editable arg0)  {
	            // TODO Auto-generated method stub
	            HomeFragment.this.adapter.getFilter().filter(arg0);

	        }
	    });
	     
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Toast.makeText(getActivity(), adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
				input.setText(adapter.getItem(arg2));
				list.setVisibility(View.GONE);
				
				int mAction = 0;
				
				for (int i = 0 ; i < display.size() ; i++){
					if(adapter.getItem(arg2).equals(display.get(i))){
						mAction = i;
					}
				}
			
				
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
					      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
					
				a.switchMapFragment(action.get(mAction));
			}
		});
	    
	    
	    /* PLANNING */
	    ArrayList<PlanningElement> l = getAllTalks();
	    Collections.sort(l);
	        
	    listPlanning= (LinearLayout) rootView.findViewById(R.id.listSchedule);
	    SparseIntArray sparseArray = new SparseIntArray();
	    
	    int max = Math.min(l.size(), 3);
	    
	    /* Display */
        for (int i = 0; i < max; i++) {

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.planning_element,listPlanning, false);
            
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.container);
            TextView start = (TextView) view.findViewById(R.id.start);
            TextView end = (TextView) view.findViewById(R.id.end);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
            View bar = view.findViewById(R.id.bar);

            int roomId = l.get(i).getId();
            if (sparseArray.get(roomId, -1) != -1){
            	bar.setBackgroundColor(sparseArray.get(roomId));
            }
            else{
            	Random rand = new Random();
                int randomColor = 0xff000000 + rand.nextInt(0xFFFFFF);
                System.out.println(randomColor);
            	sparseArray.append(roomId, randomColor);
            	bar.setBackgroundColor(randomColor);
            	bar.invalidate();
            }
            	
          
            final Talk t = l.get(i).getTalk();
            
            /* Date */
            
            Date s = new Date(t.getStartTs() * 1000);
            Date e = new Date(t.getEndTs() * 1000);
           
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

	
	private Handler handler = new Handler();

	private Runnable runnable = new Runnable() {
	   private boolean nodata;

	@Override
	   public void run() {
		   if (file.exists()) {
               updateView();
               confLayout.setVisibility(View.VISIBLE);
               if(nodata){
            	   updateCalendar();
            	   nodata = false;
               }
   			}
		   else{
			   confLayout.setVisibility(View.GONE);
			   nodata = true;
		   }
//		   System.out.println("run done");
           handler.postDelayed(this, 1000);
	   }
	};
	
	private void updateView() {
		Conference conf = ReferenceApplication.deserializeConference();
		
//		System.out.println(conf);
		
		if (conf !=null){
			title.setText(conf.getTitle());
			dates.setText("Du " + conf.getStartDay() + " au " + conf.getEndDay());
			addresse.setText("Adresse : "+conf.getAddress());
		}
			
		
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
	
	private void updateCalendar(){
		
	}
}
