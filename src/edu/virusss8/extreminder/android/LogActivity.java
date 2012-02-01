package edu.virusss8.extreminder.android;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class LogActivity extends Activity {
	
	EditText logShow;
	Aplikacija app;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_layout);
        
        app = (Aplikacija) getApplication();
        logShow = (EditText) findViewById(R.id.etLog);
        logShow.setKeyListener(null);
        logShow.setFocusable(false);
        
        readLog();
    }
    
    public void readLog() {
    	try {
			BufferedReader br = new BufferedReader(new FileReader("sdcard/log_zdravila.txt"));
			String str;
	        StringBuilder sb = new StringBuilder();
	        
	        while ((str = br.readLine()) != null) {
	        	sb.append(str + "\n");
	        }
	        logShow.setText(sb.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_logs, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.editLogs:
			logShow.setFocusable(true);
			return true;
		case R.id.saveLogs:
			return true;		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}