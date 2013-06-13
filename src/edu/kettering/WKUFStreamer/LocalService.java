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
	private static boolean isPrepared = false; // Media Player Prepared?
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
		
		Log.d("AppStatus", "Stream Source has been set to: " + StreamURL);
	
		mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				isPrepared = true;
			}
		});
		
		prepareMediaPlayer();
		
		return mBinder;
	}
		
		
	private void prepareMediaPlayer(){
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
		
	}
	
	public int playPause() {

		if(isPrepared == true){

			if (mp.isPlaying() == true) {
	//			mp.stop(); // use stop instead of pause so that stream starts at live location
				mp.pause();
				isPlaying = false;
				return 0; // indicate paused
			} else {
				mp.start();
				isPlaying = true;
				return 1; // indicate playing
			}
		
		} else {
			prepareMediaPlayer();
			return -1;
			
		}

	}
	
	public int playingStatus(){
		if(isPlaying == true){
			return 1;
		}else{
			return 0;
		}
	}
	
	public int preparedStatus(){
		if(isPrepared == true){
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