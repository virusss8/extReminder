package edu.virusss8.extreminder.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbAdapterMain implements BaseColumns {
	public static final  String TAG="DbAdapterMain";

	public static final String NAME = "s_name";
	public static final String ST_TABLET = "i_st_tablet";
	public static final String REPEAT = "i_repeat";
	public static final String HOUR = "i_hour";
	public static final String MINUTE = "i_minute";
	public static final String DAY = "i_day";
	public static final String MONTH = "i_month";
	public static final String YEAR = "i_year";
    public static final int POS__ID = 0;
	public static final int POS_NAME = 1;
	public static final int POS_ST_TABLET = 2;
	public static final int POS_REPEAT = 3;
	public static final int POS_HOUR = 4;
	public static final int POS_MINUTE = 5;
	public static final int POS_DAY = 6;
	public static final int POS_MONTH = 7;
	public static final int POS_YEAR= 8;

	public static final String TABLE = "alarms";
	
	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DbAdapterMain(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	//---opens the database---
	public DbAdapterMain open() throws SQLException 
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---    
	public void close() 
	{
		DBHelper.close();
	}

	//---insert an alarm
	public long insertAlarm(String name, int st_tablet, int repeat, int hour, int minute,
			int day, int month, int year) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(NAME, name);
		initialValues.put(ST_TABLET, st_tablet);
		initialValues.put(REPEAT, repeat);
		initialValues.put(HOUR, hour);
		initialValues.put(MINUTE, minute);
		initialValues.put(DAY, day);
		initialValues.put(MONTH, month);
		initialValues.put(YEAR, year);
		return db.insert(TABLE, null, initialValues);
	}

	//---deletes a particular title---
	public boolean deleteAlarm(long rowId) 
	{
		return db.delete(TABLE, _ID + "=" + rowId, null) > 0;
	}

	//---retrieves all the titles---
	public Cursor getAll() 
	{
		return db.query(TABLE, new String[] {
				_ID,       //POS__ID = 0;
				NAME,      //POS_NAME = 1;
				ST_TABLET, //POS_ST_TABLET = 2;
				REPEAT,   //POS_REPEAT = 3;
				HOUR,    //POS_HOUR = 4;
				MINUTE,  //POS_MINUTE = 5;
				DAY,   //POS_DAY = 6;
				MONTH,  //POS_MONTH = 7;
				YEAR},  //POS_YEAR = 8;
				null, 
				null, 
				null, 
				null, 
				null);
	}

	//---retrieves a particular title---
	public Cursor getOnlyOne(long rowId) throws SQLException 
	{
		Cursor mCursor =
			db.query(true, TABLE, new String[] {
					_ID, 
					NAME,
					ST_TABLET,
					REPEAT,
					HOUR,
					MINUTE,
					DAY,
					MONTH,
					YEAR}, 
					_ID + "=" + rowId, 
					null,
					null, 
					null, 
					null, 
					null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	//---update---
	public boolean updateOnlyOne(long rowId, String name, int st_tablet, 
			int repeat, int hour, int minute, int day, int month, int year) 
	{
		ContentValues args = new ContentValues();
		args.put(NAME, name);
		args.put(ST_TABLET, st_tablet);
		args.put(REPEAT, repeat);
		args.put(HOUR, hour);
		args.put(MINUTE, minute);
		args.put(DAY, day);
		args.put(MONTH, month);
		args.put(YEAR, year);
		return db.update(TABLE, args,
				_ID + "=" + rowId, null) > 0;
	}
}
