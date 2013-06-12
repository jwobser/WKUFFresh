package edu.kettering.WKUFStreamer;

import java.util.Locale;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
	
	
	/* ***** Notification Manager ***** */
	private NotificationManager mNotificationManager;
	
	/* ***** Player Status ***** */
	private boolean isMuted;
	private boolean isNotifying;
	private boolean isPlaying;
	
	/* ***** Intents ***** */
	private Intent webtestintent;
	private Intent testintent;
	
	/* ***** Media Player Service ***** */
	LocalService mService;
	boolean mBound = false;
	
	/* ***** Images ***** */
	Bitmap large_notification_icon;

	/* ***** Tabbed Layout Objects ***** */
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	PlayerFragment fragment1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// setContentView(R.layout.webview_test);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		webtestintent = new Intent(MainActivity.this, webtestclass.class);
		testintent = new Intent(MainActivity.this, TestActivity.class);
		Intent service = new Intent(this, LocalService.class);
		this.startService(service);
		
		

		

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		large_notification_icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_large);
		
	}
	
	
	
	public void onButtonClick(View v) {
        if (mBound) {
        	mService.playPause();
        }
    }
	
	@Override
	protected void onStart(){
		super.onStart();
		Intent intent = new Intent(this, LocalService.class);
		Log.d("AppStatus", "Binding Service");
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		if (mBound){
			unbindService(mConnection);
			mBound = false;
			
		}
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
	

