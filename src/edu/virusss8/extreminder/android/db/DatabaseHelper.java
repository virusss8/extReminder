package edu.virusss8.extreminder.android.db;

import edu.virusss8.extreminder.android.db.DbAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	public static final  String TAG="DatabaseHelper";
	private static final int DATABASE_VERSION = 57;
	private static final String DATABASE_NAME = "db_zdravila";
	
	private static final String TABLE_ZDRAVILA_CREATE =
		"create table "+DbAdapter.TABLE+" ("
		+DbAdapter._ID+" integer primary key autoincrement, "
		+DbAdapter.NAME+" TEXT not null, "
		+DbAdapter.ST_TABLET+" INT not null, "
		+DbAdapter.REPEAT+" INT not null );";
	
	private static final String TABLE_ALARMI_CREATE =
		"create table "+DbAdapterMain.TABLE+" ("
		+DbAdapterMain._ID+" integer primary key autoincrement, "
		+DbAdapterMain.NAME+" TEXT not null, "
		+DbAdapterMain.ST_TABLET+" INT not null, "
		+DbAdapterMain.REPEAT+" INT not null, "
		+DbAdapterMain.HOUR+" INT not null, "
		+DbAdapterMain.MINUTE+" INT not null, "
		+DbAdapterMain.DAY+" INT not null, "
		+DbAdapterMain.MONTH+" INT not null, "
		+DbAdapterMain.YEAR+" INT not null );";
	
	DatabaseHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(TABLE_ALARMI_CREATE);
		db.execSQL(TABLE_ZDRAVILA_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
			int newVersion) 
	{
		Log.w(TAG, "Upgrading database from version " + oldVersion 
				+ " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS "+DbAdapter.TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+DbAdapterMain.TABLE);
		onCreate(db);
	}
}
