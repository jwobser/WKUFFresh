package edu.kettering.WKUFStreamer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

	public SettingsFragment() {
		// TODO Auto-generated constructor stub
	}
	
		
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View root = inflater.inflate(R.layout.settings_fragment, container, false);
		
				
		return root;
		
	}


}
