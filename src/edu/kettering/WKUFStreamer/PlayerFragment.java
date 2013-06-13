package edu.kettering.WKUFStreamer;

import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import edu.kettering.WKUFStreamer.MainActivity;
import edu.kettering.WKUFStreamer.LocalService;
import edu.kettering.WKUFStreamer.LocalService.LocalBinder;

public class PlayerFragment extends Fragment {
	
	ToggleButton btnStreamToggle;
	ImageButton btnToggleMute;
	SeekBar seekVolume;
	ImageButton btnTogglePlay;
	LocalBinder binder;
	LocalService mService;
	
	/* ***** Player Status ***** */
	private boolean mBound;
	private int isPlaying; // 1 - Playing 0 - Not Playing
	
		
	/* ***** Audio Manager ***** */
	private AudioManager mgr = null;

	public PlayerFragment() {
		// TODO Auto-generated constructor stub
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

	public class SettingsContentObserver extends ContentObserver{
		public SettingsContentObserver(Handler handler){
			super(handler);
		}
		
		@Override
		public boolean deliverSelfNotifications(){
			return super.deliverSelfNotifications();
		}
		
		@Override
		public void onChange(boolean selfChange){
			super.onChange(selfChange);
			setVolumeSeek();
			if(mBound == true){
				int isMuted = mService.muteStatus();
				switch(isMuted){
					case 0:
						break;
					case 1:
						mService.toggleMute();
						break;
				}
			}
			
		}
	}
	
	public void setVolumeSeek(){
		seekVolume.setProgress(mgr.getStreamVolume(AudioManager.STREAM_MUSIC));
	}
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View root = inflater.inflate(R.layout.player_fragment, container, false);
	
		 btnStreamToggle = (ToggleButton) root.findViewById(R.id.btnToggleStream);
		 btnStreamToggle.setOnCheckedChangeListener(mOnCheckedChangeListener);
		 btnToggleMute = (ImageButton) root.findViewById(R.id.btnToggleMute);
		 btnToggleMute.setOnClickListener(muteClickListener);
		 seekVolume = (SeekBar) root.findViewById(R.id.seekVolume);
		 seekVolume.setOnSeekBarChangeListener(volumeChangeListener);
		 btnTogglePlay = (ImageButton) root.findViewById(R.id.btnTogglePlay);
		 btnTogglePlay.setOnClickListener(playClickListener);
		 
		 
	    Intent intent = new Intent(getActivity(), LocalService.class);
		Log.d("AppStatus", "Binding Service");
		if(mBound != true){
			getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		}else{
			Log.d("AppStatus","Service Already Bound, Skipping Bind");
		}
				
		mgr=(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
		
		seekVolume.setMax(mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		seekVolume.setProgress(mgr.getStreamVolume(AudioManager.STREAM_MUSIC));
		
		SettingsContentObserver mSettingsContentObserver = new SettingsContentObserver(new Handler());
		getActivity().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver);
				
		return root;
		
	}



/* ***** Listeners ***** */
//Toggle Pause
		private OnClickListener playClickListener = new OnClickListener(){
			@Override
			public void onClick(View view){
				Log.d("AppStatus","Toggle Play State");
				isPlaying = mService.playPause();
				switch(isPlaying){
				case -1:
					Context context = getActivity().getApplicationContext();
					String text = "Not Ready. Please Wait.";
					int duration = Toast.LENGTH_LONG;
					
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					break;
				case 1:
					btnTogglePlay.setImageResource(R.drawable.ic_pause_btn);
					break;
				case 0:
					btnTogglePlay.setImageResource(R.drawable.ic_play_btn);
					break;
				}
				 				
				}
		};
		
		// Toggle Mute
		private OnClickListener muteClickListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
			mService.toggleMute();
			 }
		};

		
		// For toggling play state
		private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
			
				int maxVolume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				// Test for returing max stream volume
				Context context = getActivity().getApplicationContext();
				String text = new String("Max Volume: ").concat(String.valueOf(maxVolume));
				int duration = Toast.LENGTH_LONG;
				
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
						
			}
		};
			
		// For changing volume
		private OnSeekBarChangeListener volumeChangeListener = new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				mgr.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
				// Need to use AudioManager 
								
				
				}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
		};
	
		
}
