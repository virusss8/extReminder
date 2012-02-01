package edu.virusss8.extreminder.android;

import edu.virusss8.extreminder.android.db.DbAdapterMain;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainAlarms extends ListActivity implements OnItemClickListener{

	private static final int ID_ADD = 1;
	private static final int ID_DELETE = 2;
	private static final int LIST_MEDICATION_ACTIVITY_ID = 3;
	QuickAction mQuickAction = null;
	
	Aplikacija app;
	TextView tv_time;
	DbAdapterMain pbA;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_alarms);
		app = (Aplikacija) getApplication();
		setListAdapter(app.bazaAlarms);
		this.getListView().setOnItemClickListener(this); // POMEMBNO!!!

		pbA = new DbAdapterMain(this);
		
		ActionItem deleteItem = new ActionItem(ID_DELETE, "Izbriši alarm", getResources().getDrawable(R.drawable.minus));
		ActionItem addItem = new ActionItem(ID_ADD, "Dodaj nov alarm", getResources().getDrawable(R.drawable.plus));
        
        mQuickAction = new QuickAction(this);
		
		mQuickAction.addActionItem(deleteItem);
		mQuickAction.addActionItem(addItem);
	}

	@Override
	public void onItemClick(AdapterView<?> aView, View v, final int position, long rowId) {
		mQuickAction.show(v);

		mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				if (actionId == ID_DELETE) { //Add item selected
					pbA.open();
					Cursor c = pbA.getAll();
					c.getColumnCount();
					System.out.println("Pozicija:" + position);
					c.moveToPosition(position);
					long regID = c.getLong(0);
					pbA.deleteAlarm(regID);
					c.close();
					pbA.close();
					Alarm dbElement = app.bazaAlarms.getItem(position);
					app.bazaAlarms.remove(dbElement);
				}
				else if (actionId == ID_ADD) { //Delete selected item
					openDataBase();
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_alarms, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addNewAlarm:
			openDataBase();
			return true;
		case R.id.deleteAlarms:
			app.killAllAlarms();
			Toast.makeText(this, "Ustavljen Alarm Manager", Toast.LENGTH_LONG).show();
			return true;		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void openDataBase() {
		Intent namen = new Intent(this, BazaListActivity.class);
		this.startActivityForResult(namen, LIST_MEDICATION_ACTIVITY_ID);
	}
}