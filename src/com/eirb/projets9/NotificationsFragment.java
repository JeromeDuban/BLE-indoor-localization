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
	
	public final int MAX_NOTIF = 5;
	
    LinearLayout listNotif;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        
        listNotif= (LinearLayout) rootView.findViewById(R.id.listNotif);
        
        for (int i = 0; i < MAX_NOTIF; i++) {
        	 
            System.out.println("SIZE>" + i);

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.notification_element,listNotif, false);
            
            // set Title
            TextView title = (TextView) view.findViewById(R.id.titleNotif);
       	 	title.setText("Titre Conference " + Integer.toString(i));
       	 	title.setTypeface(ReferenceApplication.fontMedium);
       	 	// set room
       	 	TextView room = (TextView) view.findViewById(R.id.roomNotif);
    	 	room.setText("TD14");
    	 	room.setTypeface(ReferenceApplication.fontLight);
    	 	//set Time
    	 	TextView time = (TextView) view.findViewById(R.id.timeNotif);
       	 	time.setText("11:00 PM");
       	 	time.setTypeface(ReferenceApplication.fontThin);

            
            listNotif.addView(view);

            
        }
        
        return rootView;
    }
}
