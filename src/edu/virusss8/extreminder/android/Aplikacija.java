package edu.virusss8.extreminder.android;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.virusss8.extreminder.android.db.DbAdapter;
import edu.virusss8.extreminder.android.db.DbAdapterMain;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;

public class Aplikacija extends Application {

	public ArrayList<DataBase> seznam;
	public ArrayList<Alarm> seznamAlarms;
	BazaArrayAdapter baza;
	BazaAArayAdapter bazaAlarms;
	DbAdapter db;
	DbAdapterMain dbMain;
	
	public PendingIntent mAlarmSender; //ALARM
	int trenutnaUra, trenutnaMinuta, sekunda, nastavljenaUra, nastavljenaMinuta, position, i;
	String name;
	Date dt;
	Cursor c;
	boolean isMediaRunning, isFound, isFirst;
	Intent alarmIntent;
	AlarmService alarmService;
	
	String lokacijaLekarne;
	public String getLokacijaLekarne() {
		return lokacijaLekarne;
	}
	public void setLokacijaLekarne(String lokacijaLekarne) {
		this.lokacijaLekarne = lokacijaLekarne;
	}
	int rowIdAlarma;
	public int getRowIdAlarma() {
		return rowIdAlarma;
	}
	public void setRowIdAlarma(int rowIdAlarma) {
		this.rowIdAlarma = rowIdAlarma;
	}
	int wZdr;
	public int getwZdr() {
		return wZdr;
	}
	public void setwZdr(int wZdr) {
		this.wZdr = wZdr;
	}
	int language;
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	int stevec;
	public int getStevec() {
		return stevec;
	}
	public void setStevec(int stevec) {
		this.stevec = stevec;
	}
	private List<String> seznamZdr;
	public List<String> getSeznamZdr() {
		return seznamZdr;
	}
	public void setSeznamZdr(List<String> seznamZdr) {
		this.seznamZdr = seznamZdr;
	}
	int allList;
	public int getAllList() {
		return allList;
	}
	public void setAllList(int allList) {
		this.allList = allList;
	}
	int stevecSeznama;
	public int getStevecSeznama() {
		return stevecSeznama;
	}
	public void setStevecSeznama(int stevecSeznama) {
		this.stevecSeznama = stevecSeznama;
	}
		
	@Override
	public void onCreate() {
		super.onCreate();
		
		setwZdr(-1);
		setLanguage(1);
		setStevec(0);
		
		sekunda = 0;
		trenutnaUra = 0;
		trenutnaMinuta = 0;
		nastavljenaUra = 0;
		nastavljenaMinuta = 0;
		i = 0;
		name = "";
		position = 0;
		c = null;
		
		db = new DbAdapter(this);
		dbMain = new DbAdapterMain(this);

		//addDBA();
		fillFromDBA();
		addDB();
		fillFromDB();

		baza = new BazaArrayAdapter(this, R.layout.baza_layout, seznam);
		bazaAlarms = new BazaAArayAdapter(this, R.layout.alarmi_layout, seznamAlarms);
		
		alarmService = new AlarmService();
		alarmIntent = new Intent(Aplikacija.this, alarmService.getClass());
		mAlarmSender = PendingIntent.getService(Aplikacija.this,
				0, alarmIntent, 0);
	}
	
	public void mAlarmStart() {		
		
		isFound = false;
		isFirst = false;
		i = 0;
		nastavljenaMinuta = 0;
		nastavljenaUra = 0;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		dt = new Date();
		trenutnaUra = dt.getHours();
		trenutnaMinuta = dt.getMinutes();
		
		dbMain.open();

		c = dbMain.getAll();
		
		if(c.getCount() == 1) {
			isFirst = true;
			c.moveToFirst();
			name = c.getString(1);
			nastavljenaMinuta = c.getInt(5);
			nastavljenaUra = c.getInt(4);
			
			int tmp = dt.getHours() + c.getInt(3);
			int tmp2 = tmp-24;
			
			if(c.getInt(4) + c.getInt(3) == 24)
				dbMain.updateOnlyOne(c.getLong(0), c.getString(1), c.getInt(2)-1, c.getInt(3), 0, c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8));
			else if(c.getInt(4) + c.getInt(3) < 24)
				dbMain.updateOnlyOne(c.getLong(0), c.getString(1), c.getInt(2)-1, c.getInt(3), c.getInt(4)+c.getInt(3), c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8));
			else if(c.getInt(4) + c.getInt(3) > 24)
				dbMain.updateOnlyOne(c.getLong(0), c.getString(1), c.getInt(2)-1, c.getInt(3), tmp2, c.getInt(5), c.getInt(6)+1, c.getInt(7), c.getInt(8));
		}
		else {
			System.out.println("COUNTER: "+c.getCount());
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				if(c.getInt(6) == dt.getDate()) {
					if(i == 0) {
						i++;
						nastavljenaUra = c.getInt(4); // ura
						nastavljenaMinuta = c.getInt(5); // minuta
						position = c.getPosition();
						name = c.getString(1);
						isFound = true;
					}
					
					if((c.getInt(4) > trenutnaUra) || (c.getInt(4) == trenutnaUra && c.getInt(5) > trenutnaMinuta) || (c.getInt(4) == trenutnaUra && c.getInt(5) == trenutnaMinuta)) {
						if(nastavljenaUra > c.getInt(4) || (nastavljenaUra == c.getInt(4) && nastavljenaMinuta > c.getInt(5))) {
							isFound = true;
							nastavljenaUra = c.getInt(4); // ura
							nastavljenaMinuta = c.getInt(5); // minuta
							position = c.getPosition();
							name = c.getString(1);
						}
					}
				}
			}
		}
		
		if(isFound) {
			c.moveToPosition(position);
			int tmp = dt.getHours() + c.getInt(3);
			int tmp2 = tmp-24;
			
			if(c.getInt(4) + c.getInt(3) == 24)
				dbMain.updateOnlyOne(c.getLong(0), c.getString(1), c.getInt(2)-1, c.getInt(3), 0, c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8));
			else if(c.getInt(4) + c.getInt(3) < 24)
				dbMain.updateOnlyOne(c.getLong(0), c.getString(1), c.getInt(2)-1, c.getInt(3), c.getInt(4)+c.getInt(3), c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8));
			else if(c.getInt(4) + c.getInt(3) > 24)
				dbMain.updateOnlyOne(c.getLong(0), c.getString(1), c.getInt(2)-1, c.getInt(3), tmp2, c.getInt(5), c.getInt(6)+1, c.getInt(7), c.getInt(8));
		}
		else if(isFirst) {
			c.close();
			dbMain.close();
		}
		else {
			c.close();
			dbMain.close();
			return;
		}
		
		if(!isFirst) {
			c.close();
			dbMain.close();
		}
		
		if(nastavljenaUra == trenutnaUra && nastavljenaMinuta == trenutnaMinuta) {
			sekunda = 0;
		}
		else if(((nastavljenaUra == trenutnaUra) && (nastavljenaMinuta > trenutnaMinuta)) || (nastavljenaUra > trenutnaUra)) {
			sekunda = ((nastavljenaUra*60)+nastavljenaMinuta) - ((trenutnaUra*60)+trenutnaMinuta);
		}
		else if (nastavljenaUra < trenutnaUra) {
			sekunda = (1440 - ((trenutnaUra*60)+trenutnaMinuta)) + ((nastavljenaUra * 60) + nastavljenaMinuta);
		}
		else sekunda = 0;

		sekunda = sekunda * 60;
		
			
		isMediaRunning = true;
		calendar.add(Calendar.SECOND, sekunda);

//		Toast.makeText(Aplikacija.this, "Alarm ob: " + nastavljenaUra + ":" + nastavljenaMinuta + "SEK: " + sekunda,
//				Toast.LENGTH_LONG).show();
		
		// Schedule the alarm!
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
//		am.setRepeating(AlarmManager.RTC_WAKEUP,
//				calendar.getTimeInMillis(), 1, mAlarmSender);
		am.set(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), mAlarmSender);
	}
	
	public void setupNextAlarm() {
	}
	
	public void mAlarmStop() {
		
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(mAlarmSender);

		isMediaRunning = false;
		
		mAlarmStart();
		
//        Toast.makeText(Aplikacija.this, "STOPPED!!!",
//                Toast.LENGTH_LONG).show();
	}
	
	public void killAllAlarms() {
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(mAlarmSender);

		isMediaRunning = false;
	}

	public void fillFromDB() {
		db = new DbAdapter(this);
		db.open();
		Cursor c = db.getAll();
		seznam = new ArrayList<DataBase>();
		DataBase tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new DataBase();
			tmp.setDbID(c.getLong(DbAdapter.POS__ID));
			tmp.setName(c.getString(DbAdapter.POS_NAME));
			tmp.setSt_tablet(c.getInt(DbAdapter.POS_ST_TABLET));
			tmp.setEveryXHours(c.getInt(DbAdapter.POS_REPEAT));
			seznam.add(tmp);
		}
		c.close();
		db.close();
	}

	public void addDB() {
		db.open();
		Cursor c = db.getAll();
		if (c.getCount()== 0)
		{
			db.insertMedication("ACIPAN", 15, 10);        
			db.insertMedication("AMOKSIKLAV", 10, 12);
			db.insertMedication("BIVACYN", 20, 8);
			db.insertMedication("CANDEA", 20, 2);
			db.insertMedication("CANDEA HCT", 10, 2);
			db.insertMedication("DERMAZIN", 10, 8);
			db.insertMedication("ELDERIN", 10, 8);
			db.insertMedication("EPUFEN", 8, 8);
			db.insertMedication("FLUKONAZOL LEK", 10, 4);
			db.insertMedication("GEMCITABIN LEK", 12, 4);
			db.insertMedication("INDOFORT", 20, 4);
			db.insertMedication("KETONAL", 20, 8);
			db.insertMedication("KLIMICIN", 10, 2);
			db.insertMedication("KVELUX", 12, 2);
			db.insertMedication("LEKOKLAR", 8, 8);
			db.insertMedication("LENDACIN", 8, 8);
			db.insertMedication("NAKOM", 3, 4);
			db.insertMedication("OLIVIN", 12, 4);
			db.insertMedication("ORTANOL", 6, 4);
			db.insertMedication("OSPAMOX", 8, 6);
			db.insertMedication("PALUXON", 20, 6);
			db.insertMedication("PIRAMIL", 20, 8);
			db.insertMedication("PIRAMIL H", 20, 8);
			db.insertMedication("RANITAL", 10, 12);
			db.insertMedication("SPASMEX", 20, 12);
			db.insertMedication("TAFEN NASAL", 10, 6);
			db.insertMedication("TORNETIS", 10, 8);
			db.insertMedication("VENOFER", 16, 4);
			db.insertMedication("VOXIN", 8, 6);
			db.insertMedication("ZAPILUX", 16, 8);
			db.insertMedication("ZARZIO", 10, 12);
		}
		c.close();
		db.close();	
	}
	
	public void fillFromDBA() {
		dbMain = new DbAdapterMain(this);
		dbMain.open();
		Cursor c = dbMain.getAll();
		seznamAlarms = new ArrayList<Alarm>();
		Alarm tmp;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tmp = new Alarm();
			tmp.setDbID(c.getLong(DbAdapterMain.POS__ID));
			tmp.setName(c.getString(DbAdapterMain.POS_NAME));
			tmp.setSt_tablet(c.getInt(DbAdapterMain.POS_ST_TABLET));
			tmp.setRepeat(c.getInt(DbAdapterMain.POS_REPEAT));
			tmp.setHour(c.getInt(DbAdapterMain.POS_HOUR));
			tmp.setMinute(c.getInt(DbAdapterMain.POS_MINUTE));
			tmp.setMinute(c.getInt(DbAdapterMain.POS_DAY));
			tmp.setMinute(c.getInt(DbAdapterMain.POS_MONTH));
			tmp.setMinute(c.getInt(DbAdapterMain.POS_YEAR));
			seznamAlarms.add(tmp);
		}
		c.close();
		dbMain.close();
	}

	public void addDBA() {
		dbMain.open();
		Cursor c = dbMain.getAll();
		if (c.getCount()== 0)
		{
		}
		c.close();
		dbMain.close();	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}