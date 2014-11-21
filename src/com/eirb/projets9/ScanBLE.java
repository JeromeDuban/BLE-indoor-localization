package com.eirb.projets9;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ScanBLE extends Fragment implements BluetoothAdapter.LeScanCallback{
	
	public ScanBLE(){}
	
	private BluetoothAdapter mBluetoothAdapter;
	private Activity a;
	private Button button;
	private boolean isRunning = false;
	private TextView logs;
	private TextView status;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
        a = getActivity();
        button = (Button) rootView.findViewById(R.id.button);
        logs = (TextView) rootView.findViewById(R.id.logs);
        status = (TextView) rootView.findViewById(R.id.status);
        
        BluetoothManager manager = (BluetoothManager) a.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = manager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 0);
        }
        
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!isRunning){
					mBluetoothAdapter.startLeScan(ScanBLE.this);
					logs.setText(logs.getText().toString()+"\n SCAN STARTED");
					status.setText("SCAN RUNNING");
					status.setTextColor(Color.GREEN);
				}
				else{
					mBluetoothAdapter.stopLeScan(ScanBLE.this);
					logs.setText(logs.getText().toString()+"\n SCAN STOPPED");
					status.setText("SCAN STOPPED");
					status.setTextColor(Color.RED);
				}
				
				
				isRunning = !isRunning;
			}
		});
         
        return rootView;
    }

	@Override
	public void onLeScan(final BluetoothDevice arg0, final int arg1, final byte[] arg2) {
		
		
		
		
		System.out.println(logs.getText());
		
		// To be updated, the setText needs to be done in the main UI
		new Handler(Looper.getMainLooper()).post(new Runnable() {
		    @Override
		    public void run() {
		    	logs.setText(logs.getText().toString()+"\n " 
						+ " name : " + arg0.getName()
						+ " | addr : " + arg0.getAddress()
						+ " | rssi : " + Integer.toString(arg1) + "\n"
						+ bytesToHex(arg2) + "\n");	
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
