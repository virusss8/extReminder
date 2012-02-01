package edu.virusss8.extreminder.android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import edu.virusss8.extreminder.android.db.DbAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Ring extends Activity {

	Button btn;
	Aplikacija app;
	DbAdapter dbA;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ring);
		app = (Aplikacija) getApplication(); 
		btn = (Button) findViewById(R.id.btnStop);
		btn.setOnClickListener(klik);
    	dbA = new DbAdapter(this);
	}
	
	private OnClickListener klik = new OnClickListener() {
        public void onClick(View v) {
        	app.mAlarmStop();
        	appendLog(getAlarmObject());
        	finish();
        }
    };
    
    public String getAlarmObject(){
    	dbA.open();
    	String log;
    	Cursor c = dbA.getOnlyOne(app.getRowIdAlarma()+1);
    	log = c.getString(1);
    	c.close();
    	dbA.close();
    	
    	return log;
    }
    
    public void appendLog(String text) {       
    	File logFile = new File("sdcard/log_zdravila.txt");
    	if (!logFile.exists())
    	{
    		try
    		{
    			logFile.createNewFile();
    		} 
    		catch (IOException e)
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	try
    	{
    		//BufferedWriter for performance, true to set append to file flag
    		BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
    		buf.append(text + "\n");
    		Date cal = Calendar.getInstance().getTime(); 
    		buf.append(cal.toLocaleString() + "\n");
    		buf.newLine();
    		buf.close();
    	}
    	catch (IOException e)
    	{
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
}
