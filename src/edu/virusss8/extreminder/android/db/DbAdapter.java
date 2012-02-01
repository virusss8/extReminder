package edu.virusss8.extreminder.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbAdapter implements BaseColumns {
	public static final  String TAG="DbAdapter";

	public static final String NAME = "s_name";
	public static final String ST_TABLET = "i_st_tablet";
	public static final String REPEAT = "i_repeat";
    public static final int POS__ID = 0;
	public static final int POS_NAME = 1;
	public static final int POS_ST_TABLET = 2;
	public static final int POS_REPEAT = 3;

	public static final String TABLE = "zdravila";
	
	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DbAdapter(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	//---opens the database---
	public DbAdapter open() throws SQLException 
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---    
	public void close() 
	{
		DBHelper.close();
	}

	//---insert the medication
	public long insertMedication(String name, int st_tablet, int repeat) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(NAME, name);
		initialValues.put(ST_TABLET, st_tablet);
		initialValues.put(REPEAT, repeat);
		return db.insert(TABLE, null, initialValues);
	}

	//---deletes a particular title---
	public boolean deleteMedication(long rowId) 
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
				REPEAT}, //POS_REPEAT = 3;
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
					REPEAT}, 
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
	public boolean updateOnlyOne(long rowId, String name, String st_tablet, 
			String repeat) 
	{
		ContentValues args = new ContentValues();
		args.put(NAME, name);
		args.put(ST_TABLET, st_tablet);
		args.put(REPEAT, repeat);
		return db.update(TABLE, args, 
				_ID + "=" + rowId, null) > 0;
	}
}
