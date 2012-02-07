package edu.virusss8.extreminder.android;

import java.net.UnknownHostException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import edu.virusss8.extreminder.android.db.DbAdapter;
import edu.virusss8.extreminder.android.db.DbAdapterMain;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class BazaListActivity extends ListActivity implements OnItemClickListener{
	Aplikacija app;
	DbAdapter pbZ;
	DbAdapterMain pbAlarm;
	
	private static final int DIALOG_ADD_TO_DATABASE = 1;
	private static final int DIALOG_NEW_ALARM = 2;
	
	private static final int ID_ADD_NEW_ALARM = 3;
	private static final int ID_ADD_NEW_MEDICATION = 4;
	private static final int ID_DELETE = 5;
	int pos;
	boolean isLong;
	
	private static String IP = "164.8.119.204";
	private static String SOAP_ACTION="";
	private static String METHOD_NAME="";
	private static String NAMESPACE="";
	private static String URL="";
	private static final String TAG = "Dodajanje_Web";
	
	private View textEntryView1, textEntryView;
	private TimePicker tp;
	private DatePicker dp;
	private EditText zdravilo, tablet, naVsakeX, name, stTablet, everyXHours;
	
	private ProgressDialog dialogWait;
	
	QuickAction mQuickAction = null;
	
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);
		app = (Aplikacija) getApplication();
		setListAdapter(app.baza);
		this.getListView().setOnItemClickListener(this); // POMEMBNO!!!
		pbZ = new DbAdapter(this);
		pbAlarm = new DbAdapterMain(this);
		pos = 0;
		isLong = false;
		
		ActionItem addAlarmItem = new ActionItem(ID_ADD_NEW_ALARM, "Dodaj nov alarm", getResources().getDrawable(R.drawable.plus));
		ActionItem addMedicationItem = new ActionItem(ID_ADD_NEW_MEDICATION, "Dodaj novo zdravilo", getResources().getDrawable(R.drawable.plus));
		ActionItem deleteItem = new ActionItem(ID_DELETE, "Odstrani zdravilo", getResources().getDrawable(R.drawable.minus));
        
        mQuickAction = new QuickAction(this);
		
		mQuickAction.addActionItem(addAlarmItem);
		mQuickAction.addActionItem(addMedicationItem);
		mQuickAction.addActionItem(deleteItem);
		
		preferences = getSharedPreferences("Alarm", Context.MODE_PRIVATE);
    	editor = preferences.edit();
	}

	@Override
	public void onItemClick(AdapterView<?> aView, View v, final int position, long rowId) {
		pos = (int) (rowId);
		
		mQuickAction.show(v);
		
		mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				if (actionId == ID_ADD_NEW_ALARM) { //Add item selected
					showDialog(DIALOG_NEW_ALARM);
				} else if (actionId == ID_ADD_NEW_MEDICATION) {
					showDialog(DIALOG_ADD_TO_DATABASE);
				} else if (actionId == ID_DELETE) {
					pbZ.open();
					Cursor c = pbZ.getAll();
					c.getColumnCount();
					System.out.println("Pozicija:" + position);
					c.moveToPosition(position);
					long regID = c.getLong(0);
					pbZ.deleteMedication(regID);
					c.close();
					pbZ.close();
					DataBase dbElement = app.baza.getItem(position);
					app.baza.remove(dbElement);
				}	
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_new_to_database, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.newToDB:
			showDialog(DIALOG_ADD_TO_DATABASE);
			return true;	
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public Dialog onCreateDialog(int id) {
		switch(id) {
		case DIALOG_ADD_TO_DATABASE:
            LayoutInflater factory = LayoutInflater.from(this);
            textEntryView = factory.inflate(R.layout.dialog_add_to_database, null);
            name = (EditText) textEntryView.findViewById(R.id.et_add_name);
            name.setText("");
            stTablet = (EditText) textEntryView.findViewById(R.id.et_add_st_tablet);
            stTablet.setText("");
            everyXHours = (EditText) textEntryView.findViewById(R.id.et_add_repeat);
            everyXHours.setText("");
            return new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Vnesi novo zdravilo:")
                .setView(textEntryView)
                .setPositiveButton("Potrdi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	String one = name.getText().toString().toUpperCase();
                    	int two = Integer.parseInt(stTablet.getText().toString());
                    	int three = Integer.parseInt(everyXHours.getText().toString());
                    	pbZ.open();
                    	long id = pbZ.insertMedication(one, two, three);
                    	pbZ.close();
                    	DataBase dbElement = new DataBase(one, two, three, id);
                    	app.baza.add(dbElement);
                    	removeDialog(DIALOG_ADD_TO_DATABASE);
                    }
                })
                .setNegativeButton("Preklièi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .create();
            
		case DIALOG_NEW_ALARM:
			app.setRowIdAlarma(pos);
			pbZ.open();
			Cursor ccc = pbZ.getAll();
			ccc.moveToPosition(pos);
			LayoutInflater factory1 = LayoutInflater.from(this);
            textEntryView1 = factory1.inflate(R.layout.dialog_add_alarm, null);
            tp = (TimePicker) textEntryView1.findViewById(R.id.timePicker_alarm);
            tp.setIs24HourView(true);
            dp = (DatePicker) textEntryView1.findViewById(R.id.datePicker_alarm);
            zdravilo = (EditText) textEntryView1.findViewById(R.id.et1);
            zdravilo.setText(ccc.getString(1));
            tablet = (EditText) textEntryView1.findViewById(R.id.et2);
            tablet.setText(ccc.getInt(2) + "");
            naVsakeX = (EditText) textEntryView1.findViewById(R.id.et3);
            naVsakeX.setText(ccc.getInt(3) + "");
            ccc.close();
            pbZ.close();
            return new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Nov alarm:")
                .setView(textEntryView1)
                .setPositiveButton("Potrdi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	pbAlarm.open();
                    	String ime = zdravilo.getText().toString().toUpperCase();
                    	int two = Integer.parseInt(tablet.getText().toString().toUpperCase());
                    	int three = Integer.parseInt(naVsakeX.getText().toString().toUpperCase());
                    	int ura = tp.getCurrentHour();
                    	int minuta = tp.getCurrentMinute();
                    	int dan = dp.getDayOfMonth();
                    	int mesec = dp.getMonth();
                    	int leto = dp.getYear();
                    	long id = pbAlarm.insertAlarm(ime, two, three, ura, minuta, dan, mesec, leto);
                    	Alarm dbElement = new Alarm(ime, two, three, ura, minuta, dan, mesec, leto, id);
                    	app.bazaAlarms.add(dbElement);
                    	pbAlarm.close();
                    	app.mAlarmStart();
//                    	AlarmService as = new AlarmService();
//                    	as.mAlarmStart();
                    	Toast.makeText(BazaListActivity.this, "Alarm nastavljen ob " + ura + ":" + minuta, Toast.LENGTH_LONG).show();
//                    	sendToWeb(ime, ""+ura, ""+minuta); // IIS web service (RAIN)
                    	removeDialog(DIALOG_NEW_ALARM);
                    }
                })
                .setNegativeButton("Preklièi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	removeDialog(DIALOG_NEW_ALARM);
                    }
                })
                .create();
            
		default:
			System.out.println("Napaka - BazaListActivity - switch!!!");
		}
		return null;
	}
	
	public void sendToWeb(String ime, String ura, String minuta){
		try {
			MyTask mt = new MyTask();
			SOAP_ACTION="http://tempuri.org/AddUser";
			METHOD_NAME="AddUser";
			NAMESPACE="http://tempuri.org/";
			URL="http://" + IP + "/ExtReminder.asmx";
			mt.execute(ime, ura, minuta);
		} 
		catch (Exception e) {
			Log.e(TAG, "Napaka pri dodajanju alarma! -> " + e.toString());
		}		
	}
	
	private class MyTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			dialogWait = 
				ProgressDialog.show(BazaListActivity.this, "", "Dodajanje...", true);
		}
		protected String doInBackground(String... param) {
			SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
			
			Request.addProperty("ime", param[0]);
			Request.addProperty("ura", param[1]);
			Request.addProperty("minuta", param[2]);
			
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(Request);
			
			HttpTransportSE aht = new HttpTransportSE(URL);
			try {
				aht.call(SOAP_ACTION, soapEnvelope);
				SoapPrimitive result = (SoapPrimitive) soapEnvelope.getResponse();
				System.out.println(result);
				return result.toString();
			}
			catch(UnknownHostException e) {
				return "Ni interneta!";
			}
			catch(Exception e) {
				return "Neznana napaka z opisom: " + e.toString();
			}
		}
		
		protected void onPostExecute(String rezultat) {
			System.out.println(rezultat);
			if(rezultat.equals("ni_narejeno"))
				Toast.makeText(getApplicationContext(), "Error has occured!", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), "Alarm uspešno dodan v PB na strežniku!", Toast.LENGTH_LONG).show();
			
			dialogWait.cancel();
		}
	}
}