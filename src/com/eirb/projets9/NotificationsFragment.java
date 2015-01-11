package com.eirb.projets9;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eirb.projets9.objects.Notification;

public class NotificationsFragment extends Fragment {
	
	public NotificationsFragment(){}
	
	public final int MAX_NOTIF = 5;
	public SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
	public DateFormat df = new SimpleDateFormat("HH:mm", Locale.US);
	
    private LinearLayout listNotif;
	
    private MainActivity a;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        
        listNotif= (LinearLayout) rootView.findViewById(R.id.listNotif);
        a = (MainActivity) getActivity();

        if (ReferenceApplication.notificationList != null){
//        	Toast.makeText(getActivity(), "NOT NULL " + Integer.toString(ReferenceApplication.notificationList.size()), Toast.LENGTH_SHORT).show();
        	
        	for (int i = 0; i < ReferenceApplication.notificationList.size(); i++) {
    			
    			inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			View view = inflater.inflate(R.layout.notification_element,listNotif, false);
    			
    			Notification n = ReferenceApplication.notificationList.get(i);
    			
    			RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.content);
    			
				// set Title
				TextView title = (TextView) view.findViewById(R.id.titleNotif);
				title.setText(n.getTalk().getTitle());
				title.setTypeface(ReferenceApplication.fontMedium);
				// set room
				TextView room = (TextView) view.findViewById(R.id.roomNotif);
				room.setText("Room " + n.getRoomName());
				room.setTypeface(ReferenceApplication.fontLight);
				// set Time
				TextView time = (TextView) view.findViewById(R.id.timeNotif);
				time.setText(sdf.format((new Date(n.getTimestamp()))));
				time.setTypeface(ReferenceApplication.fontThin);
				
				final Notification notif = n;
				 layout.setOnClickListener(new View.OnClickListener() {
						
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(), Description.class);
						Bundle bundle = new Bundle();
						bundle.putString("Title", notif.getTalk().getTitle());
						bundle.putString("Subtitle", notif.getRoomName());
						bundle.putString("Start", df.format(new Date(notif.getTalk().getStartTs() * 1000)));
						bundle.putString("End", df.format(new Date(notif.getTalk().getEndTs() * 1000)));
						bundle.putString("Body", notif.getTalk().getBody());
						intent.putExtras(bundle);
						
						startActivityForResult(intent, 0);
					}
					});
    			
    			
    			listNotif.addView(view);
    		}
        }
//        else
//        	Toast.makeText(getActivity(), "NULL", Toast.LENGTH_SHORT).show();
		
        
        return rootView;
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
