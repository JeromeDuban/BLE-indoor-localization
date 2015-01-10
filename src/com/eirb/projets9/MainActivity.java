package com.eirb.projets9;


import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.eirb.projets9.adapter.NavDrawerListAdapter;
import com.eirb.projets9.model.NavDrawerItem;
import com.eirb.projets9.scanner.NotificationService;
import com.eirb.projets9.scanner.RangingService;

public class MainActivity extends Activity{
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	private String conferenceFile;
	private BluetoothAdapter mBluetoothAdapter;
	
	public static final int STATUS_BLE_ENABLED = 0;
	public static final int STATUS_BLUETOOTH_NOT_AVAILABLE = 1;
	public static final int STATUS_BLE_NOT_AVAILABLE = 2;
	public static final int STATUS_BLUETOOTH_DISABLED = 3;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		conferenceFile = ReferenceApplication.conferenceFile;
		
//		startService(new Intent(this, RangingService.class));
//		startService(new Intent(this, NotificationService.class));
		
		/* Start Fading animation */
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		/* 
		 * CONNECTIVITY TESTS :
		 * Internet
		 * Bluetooth
		 * BLE
		 * */
		
		/* Is the internet connnection available ? */
		
		if(!isOnline()){
			Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
		}
		
		/* Is the bluetooth enabled ? 
		 * If not, opens a popup to enable bluetooth */
		
		 BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
	     mBluetoothAdapter = manager.getAdapter();
	     if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
	    	 Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	         startActivityForResult(enableBtIntent, 0);
	     }
		
		/* Is the BLE available on the device ? */
	     
		switch (getBleStatus(this)) {
		case STATUS_BLE_NOT_AVAILABLE:
			Toast.makeText(this, "Bluetooth Low Energy is required but not available on your device", Toast.LENGTH_LONG).show();
			break;
		case STATUS_BLUETOOTH_NOT_AVAILABLE:
			Toast.makeText(this, "Bluetooth Status not availaible", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		
		/* SLIDING MENU */
		
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "22"));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), true, "50+"));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(3); // TODO changed for the development ( displays the map instead of home)
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
//		case R.id.action_settings:
//			return true;
		case R.id.action_delete:
			deleteFile();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new com.eirb.projets9.MapFragment();
			break;
		case 2:
			fragment = new NotificationsFragment();
			break;
		case 3:
			fragment = new PlanningFragment();
			break;
		case 4:
			fragment = new ScanBLE();
			break;
		

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	// CONNECTIVITY TESTS
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	public static BluetoothAdapter getBluetoothAdapter(Context context) {
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager == null)
            return null;
        return bluetoothManager.getAdapter();
    }
		
    public static int getBleStatus(Context context) {
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return STATUS_BLE_NOT_AVAILABLE;
        }

        final BluetoothAdapter adapter = getBluetoothAdapter(context);
        // Checks if Bluetooth is supported on the device.
        if (adapter == null) {
            return STATUS_BLUETOOTH_NOT_AVAILABLE;
        }

        if (adapter.isEnabled())
            return STATUS_BLUETOOTH_DISABLED;

        return STATUS_BLE_ENABLED;
    }
	
    public void deleteFile(){
    	File file = new File(conferenceFile);
    	 
		if(file.delete()){
			ReferenceApplication.records.clear();
			Toast.makeText(this, "Delete conf done", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "Delete conf failed", Toast.LENGTH_SHORT).show();
		}
		
		file = new File(ReferenceApplication.buildingFile);
   	 
		if(file.delete()){
			ReferenceApplication.records.clear();
			Toast.makeText(this, "Delete build done", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "Delete build failed", Toast.LENGTH_SHORT).show();
		}
		
		
    }
}
