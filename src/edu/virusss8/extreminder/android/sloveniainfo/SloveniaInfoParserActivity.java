package edu.virusss8.extreminder.android.sloveniainfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.virusss8.extreminder.android.Aplikacija;
import edu.virusss8.extreminder.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SloveniaInfoParserActivity extends Activity {
    
	public static final String PREF_NAME="nastavitve";  //pref ime, kamor se shranjujejo pref.
	ImageView nowifi, logo;
	TextView title, hello;
	ImageButton ib1, ib2;
	Aplikacija app;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        super.onCreate(savedInstanceState);
        
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        
        
        try {
			if(! (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting() || 
					cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting())) {
				toastForConnection("NISI POVEZAN NA MOBILNI INTERNET ALI WIFI OMREŽJE!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
        
        try {
//        	cm.getActiveNetworkInfo().isAvailable();
        	URL url = new URL("http://www.slovenia.info");
        	HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
        	urlc.setRequestProperty("User-Agent", "Android Application: SloveniaInfoParser");
        	urlc.setRequestProperty("Connection", "close");
        	urlc.setConnectTimeout(1000 * 30); // mTimeout is in seconds
        	urlc.connect();
        	if (urlc.getResponseCode() == 200) {
        		
                setContentView(R.layout.zacetna);
        		app = (Aplikacija) getApplication();
                title = (TextView) findViewById(R.id.title);
        		hello = (TextView) findViewById(R.id.hello);
        		logo = (ImageView) findViewById(R.id.logo);
        		ib1 = (ImageButton) findViewById(R.id.ibSlovenia);
        		ib1.setOnClickListener(new OnClickListener() {
        			
//        			@Override
        			public void onClick(View v) {
        				Uri naslov = Uri.parse("http://www.slovenia.info/");
        		        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, naslov);
        		        startActivity(launchBrowser);
        			}
        		});
        		ib2 = (ImageButton) findViewById(R.id.ibWellness);
        		ib2.setOnClickListener(new OnClickListener() {
        			
//        			@Override
        			public void onClick(View v) {
        				Intent namen = new Intent(SloveniaInfoParserActivity.this, ZdraviliscaListActivity.class);
        				SloveniaInfoParserActivity.this.startActivity(namen);
        			}
        		});
        	}
        } catch (MalformedURLException e1) {
        	// TODO Auto-generated catch block
        	e1.printStackTrace();
        	toastForConnection("ÈEPRAV SI POVEZAN V OMREŽJE, NIMAŠ DOSTOPA DO INTERNETA!");
        } catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        	toastForConnection("ÈEPRAV SI POVEZAN V OMREŽJE, NIMAŠ DOSTOPA DO INTERNETA!");
        }
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_nastavitve, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.lang:
			Intent namen = new Intent();
			namen.setClass(this, Nastavitve.class);
			startActivity(namen);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void toastForConnection(String opis) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
				(ViewGroup) findViewById(R.id.toast_layout_root));

		nowifi = (ImageView) layout.findViewById(R.id.nowifi);
		nowifi.setImageResource(R.drawable.nowifi);
		TextView text = (TextView) layout.findViewById(R.id.toasttext);
		text.setText(opis);

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
		
		finish();
	}
}