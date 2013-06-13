package edu.kettering.WKUFStreamer;

import java.util.Locale;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import edu.kettering.WKUFStreamer.LocalService;
import edu.kettering.WKUFStreamer.LocalService.LocalBinder;
import edu.kettering.WKUFStreamer.AboutFragment;
import edu.kettering.WKUFStreamer.SettingsFragment;
import edu.kettering.WKUFStreamer.CalendarFragment;
import edu.kettering.WKUFStreamer.PlayerFragment;


public class MainActivity extends FragmentActivity {
	
	
	/* ***** Connectivity Manager ***** */
	ConnectivityManager conMgr;
	
	/* ***** Player Status ***** */
//	private boolean isMuted;
//	private boolean isNotifying;
//	private boolean isPlaying;
	
	/* ***** Media Player Service ***** */
	LocalService mService;
	boolean mBound = false;
	boolean mStarted = false;
	
	/* ***** Images ***** */
	Bitmap large_notification_icon;

	/* ***** Tabbed Layout Objects ***** */
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	Intent service;
	
	@Override
	public void onNewIntent(Intent intent){
		super.onNewIntent(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
	}
	
	
	// Check for network and 
	private void NetworkCheck(){
		conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (conMgr.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||  conMgr.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING  ) {
			Log.d("AppStatus","Network is Connected OR Connecting");
		service = new Intent(this, LocalService.class);
		bindServiceIfNeeded();
		}
		else if ( conMgr.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||  conMgr.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
		    //notify user you are not online
			Log.d("AppStatus", "Network not Connected!");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Warning, No Internet Connectivity")
					.setPositiveButton("Ignore", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
					})
					.setNegativeButton("Go to Network Settings", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
							
						}
					});
		AlertDialog mDialog = builder.create();
		mDialog.show();
				
		}
	}
	
	public void bindServiceIfNeeded(){
		Intent intent = new Intent(this, LocalService.class);
		
		if(mBound == false){
			Log.d("AppStatus", "Binding Service");
			bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
			mBound = true;
		} else {
			// Already Bound, do nothing.
		}
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		}
	
	@Override
	public void onResume(){
		super.onResume();
		NetworkCheck();
	}
	
			
	private ServiceConnection mConnection = new ServiceConnection(){
	
		
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;
						
		}
	
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
		Fragment fragment = null;
			switch(position){
				case 0:
					fragment = new PlayerFragment();
					break;
				case 1:
					fragment = new SettingsFragment();
					break;
				case 2:
					fragment = new CalendarFragment();
					break;
				case 3:
					fragment = new AboutFragment();	
					break;
				}

			
			// Fragment fragment = new DummySectionFragment();
			//	Bundle args = new Bundle();
			//	args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			//	fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 4 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_Player).toUpperCase(l);
			case 1:
				return getString(R.string.title_Settings).toUpperCase(l);
			case 2:
				return getString(R.string.title_Calendar).toUpperCase(l);
			case 3:
				return getString(R.string.title_About).toUpperCase(l);
			}
			return null;
		}
	}
	
	
	
	}
	

