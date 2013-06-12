package edu.kettering.WKUFStreamer;

import java.io.IOException;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {

	/* ***** Binder ***** */
	private final IBinder mBinder = new LocalBinder();
		
	/* ***** Variables ***** */
	private static boolean isPlaying; // Is media player playing?
	private static boolean isMuted; // is player muted?
	private static final String StreamURL = "http://audio.moses.bz:80/wkuf-lp";

	/* ***** Media Player ***** */
	MediaPlayer mp;
	
	
	public class LocalBinder extends Binder {
		public LocalBinder() {
			// TODO Auto-generated constructor stub
		}
		
		LocalService getService(){
			return LocalService.this;
		}
	}


		
	@Override
	public IBinder onBind(Intent intent){
		
		isPlaying = false;
		mp = new MediaPlayer();
		mp.setVolume(1, 1); // Set volume to max scale by default. Will
							// need to adjust using system volume.

		Log.d("AppStatus", "Setting Stream Source");
		try { // Try setting stream source
			mp.setDataSource(StreamURL);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("AppStatus", "Stream Source has been set to" + StreamURL);
	
	Log.d("AppStatus", "Asynchronously Prepare Streamer");
	try {
		mp.prepareAsync();
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


		return mBinder;
	}
		
	public int playPause() {

		if (isPlaying == true) {
			mp.pause();
			isPlaying = false;
			return 0; // indicate paused
		} else {
			mp.start();
			isPlaying = true;
			return 1; // indicate playing
		}

	}
	
	public int playingStatus(){
		if(isPlaying == true){
			return 1;
		}else{
			return 0;
		}
	}
	
	public int toggleMute(){
		if(isMuted == true){
			mp.setVolume(1, 1);
			isMuted = false;
			return 0; // Not muted
		}else{
			mp.setVolume(0, 0);
			isMuted = true;
			return 1; // muted
		}
		
	}
	
	public int muteStatus(){
		if(isMuted == true){
			return 1;
		}else{
			return 0;
		}
	}
	
	
	
	public LocalService() {
		// TODO Auto-generated constructor stub
	}



}