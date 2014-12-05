package com.eirb.projets9;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eirb.projets9.objects.Conference;

public class HomeFragment extends Fragment {
		
	public HomeFragment(){}
	
	File file;
	TextView title;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        title = (TextView) rootView.findViewById(R.id.txtLabel);
        
        file = new File(ReferenceApplication.conferenceFile);
        if (file.exists()) {
			updateView();
		}
        else{
        	handler.postDelayed(runnable, 100);
        }
        return rootView;
    }

//	private void updateDisplay() {
//		final Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//    		
//			@Override
//			public void run() {
//				
//					
//			}
//		}, 0, 1000);// Update text every second
//
//	}
	
	private Handler handler = new Handler();

	private Runnable runnable = new Runnable() {
	   @Override
	   public void run() {
		   if (file.exists()) {
               updateView();
   			}
		   else{
			   handler.postDelayed(this, 100);
		   }
	      
	   }
	};
	
	private void updateView() {
		Conference conf = ReferenceApplication.deserializeAddress();
		title.setText(conf.getTitle());
		
	}
}
