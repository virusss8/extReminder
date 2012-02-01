package edu.virusss8.extreminder.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import edu.virusss8.extreminder.android.sloveniainfo.SloveniaInfoParserActivity;

public class FirstActivity extends Activity{

	private static final int ID_DIALOG_LEKARNA = 0;
	private static final int LIST_ALARMS_ID = 1;
	private static final int LIST_MEDICATION_ACTIVITY_ID = 2;
	private static final int LOGS_ID = 3;
	private static final int MAPS_ACTIVITY_ID = 4;
	private static final int SLOVENIA_INFO_PARSER_ID = 5;
	ImageView alarms, supb, logs, earth, slovenia;
	Aplikacija app;
	private View textEntryView;
	private EditText et_lekarna;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_page);
		app = (Aplikacija) getApplication();
		alarms = (ImageView) findViewById(R.id.iv_alarms);
		supb = (ImageView) findViewById(R.id.iv_baza);
		logs = (ImageView) findViewById(R.id.iv_logs);
		earth = (ImageView) findViewById(R.id.iv_maps);
		slovenia = (ImageView) findViewById(R.id.iv_slovenia);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_alarms:
			openAlarms();
			break;
		case R.id.iv_baza:
			openDB();
			break;
		case R.id.iv_logs:
			openLog();
			break;
		case R.id.iv_maps:
			showDialog(ID_DIALOG_LEKARNA);
			break;
		case R.id.iv_slovenia:
			Intent namen = new Intent(this, SloveniaInfoParserActivity.class);
			this.startActivityForResult(namen, SLOVENIA_INFO_PARSER_ID);
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case LIST_MEDICATION_ACTIVITY_ID:
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.exit:
			finish();
			return true;
		case R.id.about:
			showAboutDialog();
			return true;			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void showAboutDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("O programu");
		alertDialog.setMessage("extReminder je opomnik za zdravila, za Vas, drage babice in dedki. " +
				"Èe želite pregledati že nastavljene alarme pritisnite ikonico zvoènika. S pritiskom na bazo " +
				"pridobite seznam že vnešenih zdravil, nad katerimi lahko zgolj s pritiskom ustvarite nov opomnik " +
				"Lahko pregledate seznam zdravil, ki ste jih jemali, ali pa na zemljevidu pogledate pot do vaše priljubljene " +
				"lekarne. Ko pa se vaše zdravljenje konèa, si lahko privošèite terapijo v katerem izmed slovenskih naravnih zdravilišè. " +
				"Veselo uporabo =)" +"\n"+
				"Copyright: tprasnikar, FERI (2010/2011)");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		   }
		});
		alertDialog.setIcon(R.drawable.ic_menu_about);
		alertDialog.show();
	}
	
	public void openDB() {
		Intent namen = new Intent(this, BazaListActivity.class);
		this.startActivityForResult(namen, LIST_MEDICATION_ACTIVITY_ID);
	}
	
	public void openMaps() {
		Intent namen = new Intent(this, KjeSemActivity.class);
		this.startActivityForResult(namen, MAPS_ACTIVITY_ID);
	}
	
	public void openAlarms() {
		Intent namen = new Intent(this, MainAlarms.class);
		this.startActivityForResult(namen, LIST_ALARMS_ID);
	}
	
	public void openLog() {
		Intent namen = new Intent(this, LogActivity.class);
		this.startActivityForResult(namen, LOGS_ID);
	}
	
	public Dialog onCreateDialog(int id) {
		switch(id) {
		case ID_DIALOG_LEKARNA:
            LayoutInflater factory = LayoutInflater.from(this);
            textEntryView = factory.inflate(R.layout.dialog_lekarna, null);
            et_lekarna = (EditText) textEntryView.findViewById(R.id.et_add_lekarna);
            et_lekarna.setText("Lekarna Mozirje, 4, 3330 Mozirje, Slovenija");
            return new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Do katere lekarne želite:")
                .setView(textEntryView)
                .setPositiveButton("Potrdi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	app.setLokacijaLekarne(et_lekarna.getText().toString());
                    	removeDialog(ID_DIALOG_LEKARNA);
                    	openMaps();
                    }
                })
                .setNegativeButton("Preklièi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .create();
		default:
			System.out.println("Napaka - Create Dialog for lekarna - switch!!!");
		}
		return null;
	}
}
