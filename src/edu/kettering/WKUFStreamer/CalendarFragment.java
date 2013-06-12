package edu.kettering.WKUFStreamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class CalendarFragment extends Fragment {

	public CalendarFragment() {
		// TODO Auto-generated constructor stub
	}
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
	// View root = inflater.inflate(R.layout.about_fragment, container, false);
	
	 View root = inflater.inflate(R.layout.calendar_fragment, container, false);
		
		WebView CalendarWebView = null;
		CalendarWebView = (WebView) root.findViewById(R.id.CalendarWebView);
		CalendarWebView.setWebViewClient(new WebViewClient());
	    CalendarWebView.getSettings().setJavaScriptEnabled(true);
	    CalendarWebView.getSettings().setBuiltInZoomControls(true);
	    CalendarWebView.getSettings().setSupportZoom(true);
	    CalendarWebView.loadUrl("http://www.google.com/calendar/embed?src=t982f1rebv73v07rles4qks84o%40group.calendar.google.com&ctz=Europe/Madrid"); 
						
		return root;
		
	}

}
