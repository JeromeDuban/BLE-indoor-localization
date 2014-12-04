package com.eirb.projets9;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationsFragment extends Fragment {
	
	public NotificationsFragment(){}
	
	int i; 
    LinearLayout listNotif;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        int i; 
        LinearLayout listNotif= (LinearLayout) rootView.findViewById(R.id.listNotif);
        for (i = 0; i < 25; i++) {
        	 
            System.out.println("SIZE>" + i);

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.notifications_list, null);
            
            // set Title
            TextView title = (TextView) view.findViewById(R.id.titleNotif);
       	 	title.setText("Titre Conference " + Integer.toString(i));
       	 	// set room
       	 	TextView room = (TextView) view.findViewById(R.id.roomNotif);
    	 	room.setText("TD14");
    	 	//set Time
    	 	TextView time = (TextView) view.findViewById(R.id.timeNotif);
       	 	time.setText("11:00");

            
            listNotif.addView(view);

            
        }
        
        return rootView;
    }
}
