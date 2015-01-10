package com.eirb.projets9;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScanBLE extends Fragment implements BluetoothAdapter.LeScanCallback{
	
	public ScanBLE(){}
	
	private BluetoothAdapter mBluetoothAdapter;
	private Activity a;
	private Button button;
	private boolean isRunning = false;
	private TextView logs;
	private TextView status;
	
//	@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
// 
//        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
//        a = getActivity();
//        button = (Button) rootView.findViewById(R.id.button);
//        logs = (TextView) rootView.findViewById(R.id.logs);
//        status = (TextView) rootView.findViewById(R.id.status);
//        
//        BluetoothManager manager = (BluetoothManager) a.getSystemService(Context.BLUETOOTH_SERVICE);
//        mBluetoothAdapter = manager.getAdapter();
//        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, 0);
//        }
//        
//        button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if (!isRunning){
//					mBluetoothAdapter.startLeScan(ScanBLE.this);
//					logs.setText(logs.getText().toString()+"\n SCAN STARTED");
//					status.setText("SCAN RUNNING");
//					status.setTextColor(Color.GREEN);
//				}
//				else{
//					mBluetoothAdapter.stopLeScan(ScanBLE.this);
//					logs.setText(logs.getText().toString()+"\n SCAN STOPPED");
//					status.setText("SCAN STOPPED");
//					status.setTextColor(Color.RED);
//				}
//				
//				
//				isRunning = !isRunning;
//			}
//		});
//        return rootView;
//    }
	
	/** Called when the activity is first created. */

	private ListView list;
//	private String array[] =
//	{ "Alice", "Célia", "Guillaume", "Jérémie", "Jérôme", "Sandrine", "Laurent", "Alexis"};
	
	
	ListView lst;
	EditText input;
	ArrayAdapter<String> adapter;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.search, container, false);

	    list = (ListView) rootView.findViewById(R.id.listView);
	    input = (EditText) rootView.findViewById(R.id.editText);
	    list.setVisibility(View.GONE);
	    
	    ArrayList<String> l = new ArrayList<String>();
	    l.add("Alice");
	    l.add("Celia");
	    l.add("Guillaume");
	    l.add("Jérémie");
	    l.add("Jérôme");
	    l.add("Sandrine");
	    l.add("Laurent");
	    l.add("Alexis");
	    
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
	            ScanBLE.this.adapter.getFilter().filter(arg0);

	        }
	    });
	     
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), adapter.getItem(arg2), Toast.LENGTH_SHORT).show();
				input.setText(adapter.getItem(arg2));
				list.setVisibility(View.GONE);
				
			}
		});
	     
	     return rootView;
	}
	
	
	/* BLE METHODS */
	
	@Override
	public void onLeScan(final BluetoothDevice arg0, final int arg1, final byte[] arg2) {
		
		System.out.println(logs.getText());
		
		new Handler(Looper.getMainLooper()).post(new Runnable() {
		    @Override
		    public void run() {
		    	logs.setText(logs.getText().toString()+"\n " 
						+ " name : " + arg0.getName()
						+ " addr : " + arg0.getAddress()
						+ " rssi : " + Integer.toString(arg1) + "\n"
						+ " major : " + bytesToHex(arg2).substring(50,54) + "\n"
						+ " minor : " + bytesToHex(arg2).substring(54,58) + "\n"
						+ " UUID : " + bytesToHex(arg2).substring(18,50) + "\n"
						+ " Advertisement : "+ bytesToHex(arg2) + "\n");	
		    }
		});
		
	}
	

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
