package edu.virusss8.extreminder.android.sloveniainfo;

import edu.virusss8.extreminder.android.Aplikacija;
import edu.virusss8.extreminder.android.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Nastavitve extends PreferenceActivity{
	SharedPreferences prefs;
	Aplikacija app;
	public static final String PREF_LANG = "LANG";
	public static int lang = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (Aplikacija) getApplication();
		addPreferencesFromResource(R.xml.menu_preferences);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		SharedPreferences settings =  PreferenceManager.getDefaultSharedPreferences(app); 
		lang =  Integer.parseInt(settings.getString(PREF_LANG, "1"));
		System.out.println("LANGUAGE = " + lang);
		app.setLanguage(lang);
		//app.setSettingsMenu(); //if something has been changed
	}
}
