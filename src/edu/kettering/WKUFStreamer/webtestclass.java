package edu.kettering.WKUFStreamer;

import android.app.Activity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webtestclass extends Activity {

	public webtestclass() {
		// TODO Auto-generated constructor stub
	}
	
	WebView webtest;

	public void onCreate(){
		Log.d("AppStatus", "Web View Test Layout Loaded");
		webtest = (WebView) findViewById(R.id.webviewtest);
		// webtest = (WebView) findViewById(R.id.webviewtest);
		webtest.setWebViewClient(new WebViewClient());
		setContentView(webtest);
		webtest.loadUrl("http://www.google.com");
		
		//webtest.loadUrl("https://www.google.com/calendar/embed?src=ldlbe0ve7fj3bdeob0j939cd4s%40group.calendar.google.com&ctz=America/New_York");
	}
	
}
