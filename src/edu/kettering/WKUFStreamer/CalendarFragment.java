package edu.kettering.WKUFStreamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class CalendarFragment extends Fragment {
	private WebView CalendarWebView;
	

	public CalendarFragment() {
		// TODO Auto-generated constructor stub
	}
	
@Override
public void onCreate(Bundle savedInstanceState){

	super.onCreate(savedInstanceState);
	setHasOptionsMenu(true);
}

@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
	super.onCreateOptionsMenu(menu, inflater); 
	inflater.inflate(R.menu.calendar_menu, menu);
}

@Override
public boolean onOptionsItemSelected(MenuItem item){
	switch(item.getItemId()){
	case R.id.refresh_calendar:
		refreshCalendar();
		return true;
	default:
		return super.onOptionsItemSelected(item);
	}
	
	
}
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
	super.onCreateView(inflater, container, savedInstanceState);
	
	// View root = inflater.inflate(R.layout.about_fragment, container, false);
	
	 View root = inflater.inflate(R.layout.calendar_fragment, container, false);
		
//	 	WebView CalendarWebView = null;
		CalendarWebView = (WebView) root.findViewById(R.id.CalendarWebView);
		CalendarWebView.setWebViewClient(new WebViewClient());
	    CalendarWebView.getSettings().setJavaScriptEnabled(true);
	    CalendarWebView.getSettings().setBuiltInZoomControls(true);
	    CalendarWebView.getSettings().setSupportZoom(true);
	    CalendarWebView.loadUrl("http://www.google.com/calendar/embed?src=t982f1rebv73v07rles4qks84o%40group.calendar.google.com&ctz=Europe/Madrid"); 
						
		return root;
		
	}

public void refreshCalendar(){
	Log.d("AppStatus","Refreshing Calendar");
	CalendarWebView.reload();
}

}
