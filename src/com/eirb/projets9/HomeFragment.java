package com.eirb.projets9;

import java.io.File;

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
        handler.postDelayed(runnable, 100);

        return rootView;
    }

	
	private Handler handler = new Handler();

	private Runnable runnable = new Runnable() {
	   @Override
	   public void run() {
		   if (file.exists()) {
               updateView();
   			}
		   else{
			   title.setText("There is no conference");
		   }
//		   System.out.println("run done");
		   handler.postDelayed(this, 1000);
	   }
	};
	
	private void updateView() {
		Conference conf = ReferenceApplication.deserializeConference();
		
		System.out.println(conf);
		
		if (conf !=null)
			title.setText(conf.getTitle());
		
	}
}
