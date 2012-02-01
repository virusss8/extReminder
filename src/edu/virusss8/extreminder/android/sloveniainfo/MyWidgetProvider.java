package edu.virusss8.extreminder.android.sloveniainfo;

import edu.virusss8.extreminder.android.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);

		updateView(context, remoteViews, R.id.widget_butt_forward, appWidgetIds, "naprej");		
		updateView(context, remoteViews, R.id.widget_butt_back, appWidgetIds, "nazaj");
		updateView(context, remoteViews, R.id.widget_text_view, appWidgetIds, "odpri");

		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	private void updateView(Context context, RemoteViews remoteViews, int viewId, 
			int[] appWidgetIds, String action) {

		Intent intent = new Intent(context.getApplicationContext(),
				UpdateWidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
		intent.setAction(action);

		PendingIntent pendingIntent = PendingIntent.getService(
				context.getApplicationContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		remoteViews.setOnClickPendingIntent(viewId, pendingIntent);
	}
}