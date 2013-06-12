package edu.kettering.WKUFStreamer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TestActivity extends Activity {

	public TestActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		Log.d("AppStatus", "Setting Layout");
		setContentView(R.layout.test_layout);
	}
	
}
