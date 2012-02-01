package edu.virusss8.extreminder.android.sloveniainfo;

import edu.virusss8.extreminder.android.Aplikacija;
import edu.virusss8.extreminder.android.R;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {

	Aplikacija app;

	public void onStart(Intent intent, int startId) {
		
		if(app == null) 
			app = (Aplikacija) getApplicationContext();

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] appWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
		
		RemoteViews remoteViews = new RemoteViews(getPackageName(),
				R.layout.widget_layout);
		
		if (appWidgetIds.length > 0) {
			for (int widgetId : appWidgetIds) {				
				setRemoteViews(remoteViews, intent);
				appWidgetManager.updateAppWidget(widgetId, remoteViews);
			}
		}
		super.onStart(intent, startId);
	}
	
	private void setRemoteViews(RemoteViews remoteViews, Intent intent) {
		if(intent.getAction().equalsIgnoreCase("naprej")) {
			remoteViews.setTextViewText(R.id.widget_text_view, app.getSeznamZdr().get(app.getStevecSeznama()));
			if(app.getStevecSeznama()+1 > app.getAllList()) {
				app.setStevecSeznama(0);
			}
			else app.setStevecSeznama(app.getStevecSeznama()+1);
		} 
		else if(intent.getAction().equalsIgnoreCase("nazaj")) {
			remoteViews.setTextViewText(R.id.widget_text_view, app.getSeznamZdr().get(app.getStevecSeznama()));
			if(app.getStevecSeznama()-1 < 0) {
				app.setStevecSeznama(app.getAllList());
			}
			else app.setStevecSeznama(app.getStevecSeznama()-1);
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}