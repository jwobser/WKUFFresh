package edu.kettering.WKUFStreamer;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsFragment extends Fragment {

	public SettingsFragment() {
		// TODO Auto-generated constructor stub
	}
	
/* ***** Layout Elements ***** */
	Switch SwitchWiFi;
	Switch SwitchHeadphones;
	EditText txtStreamSource;
	Button btnSubmitChange;
	CheckBox chkAltSource;
	
	
/* ***** Shared Prefs Editor ***** */
	SharedPreferences mPrefs;
	SharedPreferences.Editor editor;
		
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View root = inflater.inflate(R.layout.settings_fragment, container, false);
		SwitchWiFi = (Switch)root.findViewById(R.id.switch_wifi);
		SwitchWiFi.setOnCheckedChangeListener(WiFiCheckedListener);
		SwitchHeadphones = (Switch)root.findViewById(R.id.switch_headphones);
		SwitchHeadphones.setOnCheckedChangeListener(HeadphonesCheckedListener);
		txtStreamSource = (EditText)root.findViewById(R.id.txt_streamsource);
		btnSubmitChange = (Button)root.findViewById(R.id.btn_submit_change);
		btnSubmitChange.setOnClickListener(SubmitChangeListener);
		chkAltSource = (CheckBox)root.findViewById(R.id.chk_alternate_source);
		chkAltSource.setOnCheckedChangeListener(AltSourceListener);
		
		mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
		editor = mPrefs.edit();
		
		chkAltSource.setChecked(mPrefs.getBoolean("UseAlt", false));
		SwitchHeadphones.setChecked(mPrefs.getBoolean("MuteOnUnplug", false));
		SwitchWiFi.setChecked(mPrefs.getBoolean("WiFiOnly", false));
		txtStreamSource.setText(mPrefs.getString("AltSource", getActivity().getString(R.string.default_stream)));
		txtStreamSource.setEnabled(mPrefs.getBoolean("UseAlt", false));
		return root;
		
	}


/* ***** Listeners ***** */
// Play only on Wifi
private OnCheckedChangeListener WiFiCheckedListener = new OnCheckedChangeListener(){

	@Override
	public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
		editor.putBoolean("WiFiOnly", isChecked);
		editor.commit();
	}

};

// Mute on Headphone Unplug
private OnCheckedChangeListener HeadphonesCheckedListener = new OnCheckedChangeListener(){

	@Override
	public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
		editor.putBoolean("MuteOnUnplug", isChecked);
		editor.commit();
	}
};


// Use Alternate Stream Source
private OnCheckedChangeListener AltSourceListener = new OnCheckedChangeListener(){

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked == false){
			txtStreamSource.setEnabled(false);
			editor.putBoolean("UseAlt", isChecked);
		}else{
			txtStreamSource.setEnabled(true);
			editor.putBoolean("UseAlt", isChecked);
		}
		
		editor.commit();
		
	}
	
};

// Submit Changes
private OnClickListener SubmitChangeListener = new OnClickListener(){

	@Override
	public void onClick(View arg0) {
		
		String AltSource = txtStreamSource.getText().toString();
		editor.putString("AltSource", AltSource);
		editor.commit();
		
	}
	
};

}
