package com.eirb.projets9;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.eirb.projets9.map.MapModel;
import com.eirb.projets9.map.MapView;
import com.eirb.projets9.map.objects.PolygonRoom;
import com.eirb.projets9.map.objects.RectangleRoom;

public class MapFragment extends Fragment implements View.OnClickListener{



	/* -------------------- Attributes -------------------- */
	MapView map = null;
	private static Context mContext;
	private LinearLayout mLinearLayoutView;
	private View rootView;
	ArrayList<RectangleRoom> clickableRectangles = MapModel.getClickableRectangles();
	ArrayList<PolygonRoom> clickablePolygons = MapModel.getClickablePolygons();
	private ListView list;
	private EditText input;
	private ArrayAdapter<String> adapter;

	/* -------------------- Constructors -------------------- */


	/**
	 * Used by the Operating System when it killed the fragment to free memory
	 * and need to recreate it.
	 *
	 * Never use this constructor directly !
	 *
	 */
	public MapFragment(){}

	/* -------------------- Methods -------------------- */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		
		mContext = getActivity();
		rootView = inflater.inflate(R.layout.fragment_map, container, false);
		mLinearLayoutView = (LinearLayout) rootView.findViewById(R.id.map_linear_layout);
		displayData();
		
		if(this.getArguments() == null){
//			Toast.makeText(getActivity(), "NULL", Toast.LENGTH_SHORT).show();
			Log.d("MAP", "New instance");
		}
		else{
//			Toast.makeText(getActivity(), ">"+this.getArguments().getString("room")+"<", Toast.LENGTH_SHORT).show();
			Log.d("Map","Coming from planning");
			String room = this.getArguments().getString("room");
			for(int i = 0; i < clickableRectangles.size(); i++){
				Log.d("RECTANGLE NAME", clickableRectangles.get(i).getName());
				if(room.equals(clickableRectangles.get(i).getName())){
					clickableRectangles.get(i).setState(0);
				}
				else {
					clickableRectangles.get(i).setState(3);
				}
			}

			for(int i = 0; i < clickablePolygons.size(); i++){
				Log.d("POLYGON NAME", clickablePolygons.get(i).getName());
				if(room.equals(clickablePolygons.get(i).getName())){
					clickablePolygons.get(i).setState(0);
				}
				else {
					clickablePolygons.get(i).setState(3);
				}
			}
		}

		//       research bar 
//		Spinner spinner = (Spinner) rootView.findViewById(R.id.rooms_spinner);
//		spinner.setPrompt("Go To");
//		//spinner.setOnItemSelectedListener(this);
//		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {                
//				Log.d("MAPFRAGMENT",parent.getItemAtPosition(position).toString());
//
//				for(int i = 0; i < clickableRectangles.size(); i++)
//				{
//					Log.d("RECTANGLE NAME", clickableRectangles.get(i).getName());
//					if(parent.getItemAtPosition(position).toString().equals(clickableRectangles.get(i).getName())){
//						clickableRectangles.get(i).setState(0);
//						Log.d("IF NAME ==", "HELLO");
//					}
//
//					else {
//						clickableRectangles.get(i).setState(3);
//					}
//				}
//
//				for(int i = 0; i < clickablePolygons.size(); i++)
//				{
//					Log.d("POLYGON NAME", clickablePolygons.get(i).getName());
//					if(parent.getItemAtPosition(position).toString().equals(clickablePolygons.get(i).getName())){
//						clickablePolygons.get(i).setState(0);
//						Log.d("IF NAME ==", "HELLO");
//					}
//
//					else {
//						clickablePolygons.get(i).setState(3);
//					}
//				}
//
//				//If talks starts at position 3
//				if (position >= 3) {
//					for(int i = 0; i < clickablePolygons.size(); i++)
//					{
//						if(parent.getItemAtPosition(2).toString().equals(clickablePolygons.get(i).getName())){
//							clickablePolygons.get(i).setState(0);
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//
//			}
//		});
//
//		// Create an ArrayAdapter using the string array and a default spinner layout
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity().getApplicationContext(),
//				R.array.rooms_array, R.layout.spinner_list_item);
//		// Specify the layout to use when the list of choices appears
//		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//		// Apply the adapter to the spinner
//		spinner.setAdapter(adapter);

		list = (ListView) rootView.findViewById(R.id.listView);
	    input = (EditText) rootView.findViewById(R.id.editText);
	    list.setVisibility(View.GONE);
	    
	    ArrayList<String> l = new ArrayList<String>();
	    l.add("I001");
	    l.add("I002");
	    l.add("I003");
	    l.add("I004");
	    
	    String[] array = new String[l.size()];
	    l.toArray(array);
	    

	    adapter =  new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1 , array);
	    list.setAdapter(adapter);

	    // By using setTextFilterEnabled method in listview we can filter the listview items.

	     list.setTextFilterEnabled(true);
	     input.addTextChangedListener(new TextWatcher() {

	        @Override
	        public void onTextChanged( CharSequence arg0, int arg1, int arg2, int arg3){
	            // TODO Auto-generated method stub
	        	list.setVisibility(View.VISIBLE);
	        	if(arg3 == 0)
	        		list.setVisibility(View.GONE);
	        }

	        @Override
	        public void beforeTextChanged( CharSequence arg0, int arg1, int arg2, int arg3) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void afterTextChanged( Editable arg0)  {
	            // TODO Auto-generated method stub
	            MapFragment.this.adapter.getFilter().filter(arg0);

	        }
	    });
	     
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Toast.makeText(getActivity(), adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
				input.setText(adapter.getItem(arg2));
				list.setVisibility(View.GONE);
				
				for(int i = 0; i < clickableRectangles.size(); i++){
					Log.d("RECTANGLE NAME", clickableRectangles.get(i).getName());
					if(adapter.getItem(arg2).equals(clickableRectangles.get(i).getName())){
						clickableRectangles.get(i).setState(0);
					}
					else {
						clickableRectangles.get(i).setState(3);
					}
				}

				for(int i = 0; i < clickablePolygons.size(); i++)
				{
					Log.d("POLYGON NAME", clickablePolygons.get(i).getName());
					if(adapter.getItem(arg2).equals(clickablePolygons.get(i).getName())){
						clickablePolygons.get(i).setState(0);
					}
					else {
						clickablePolygons.get(i).setState(3);
					}
				}
				
			}
		});
		
		
		handler.postDelayed(runnable, 1000);

		return rootView;
	}

	/*
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

    	System.out.println(parent.getItemAtPosition(pos).toString());
    }*/

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	/**
	 * public method to display data from SVG and JSON
	 */
	public void displayData(){
		try {
			map = new MapView(mContext, null);			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO Changed for dev (floor levels)
		Button button1, button2, button3, button4, button5, setOriginalScale;
		//		button1 = (Button) rootView.findViewById(R.id.button1);
		//		button2 = (Button) rootView.findViewById(R.id.button2);
		//		button3 = (Button) rootView.findViewById(R.id.button3);
		//		button4 = (Button) rootView.findViewById(R.id.button4);
		//		button5 = (Button) rootView.findViewById(R.id.button5);
		//		setOriginalScale = (Button) rootView.findViewById(R.id.setOriginalScaleButton);

		//		button1.setOnClickListener(this);
		//		button2.setOnClickListener(this);
		//		button3.setOnClickListener(this);
		//		button4.setOnClickLristener(this);
		//		button5.setOnClickListener(this);
		//		setOriginalScale.setOnClickListener(this);


		mLinearLayoutView.addView(map);
	}

	/* Refresh the map every second to display the new position of the user */

	private Handler handler = new Handler();

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			System.out.println("MapFragment : Going to invalidate");
			map.invalidate();
			handler.postDelayed(this, 1000);

		}
	};

	public void onDestroyView() {
		super.onDestroyView();
		handler.removeCallbacks(runnable);
	};


	@Override
	public void onClick(View v) {
		//		switch(v.getId()){
		//		
		//		// TODO changed for dev
		//		case R.id.button1:
		//			map.setStorey(-1,mContext);
		//			break;
		//
		//		case R.id.button2:
		//			map.setStorey(0,mContext);
		//			break;
		//
		//		case R.id.button3:
		//			map.setStorey(1,mContext);
		//			break;
		//
		//		case R.id.button4:
		//			map.setStorey(2,mContext);
		//			break;
		//
		//		case R.id.button5:
		//			map.setStorey(3,mContext);
		//			break;
		//
		//		case R.id.setOriginalScaleButton:
		//			map.setOriginalScale();
		//			break;
		//		}
	}


}
