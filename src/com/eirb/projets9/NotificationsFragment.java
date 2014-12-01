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
        for (i = 0; i < 30; i++) {
        	 
            System.out.println("SIZE>" + i);

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.notifications_list, null);
            
            // set Title
            //TextView title = (TextView) rootView.findViewById(R.id.titleNotif);
       	 	//title.setText("Title");
       	 	// set room
       	 	//TextView room = (TextView) rootView.findViewById(R.id.roomNotif);
    	 	//room.setText("Room");
    	 	//set Time
    	 	//TextView time = (TextView) rootView.findViewById(R.id.timeNotif);
       	 	//time.setText("Time");

            
            listNotif.addView(view);

            
        }
        
        return rootView;
    }
}
