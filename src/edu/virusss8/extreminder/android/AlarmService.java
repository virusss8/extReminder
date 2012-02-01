package edu.virusss8.extreminder.android;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.Vibrator;

public class AlarmService extends Service {

	Aplikacija app;
	Thread thr;
	NotificationManager mNM;
    Vibrator woot;
	MediaPlayer mp;
	
	boolean isRunning;
	
	public AlarmService() {
		isRunning = true;
	}
	
	@Override
    public void onCreate() {
		app = (Aplikacija) getApplication();
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
        thr = new Thread(null, mTask, "AlarmService");
        thr.start();
    }
	
	@Override
    public void onDestroy() {
		// Cancel the notification -- we use the same ID that we had used to start it

		mp.stop();
		woot.cancel();

		mNM.cancel(R.string.alarm_service_started);
		//		
		// Tell the user we stopped.
//		Toast.makeText(this, "KONÈANO!!!!", Toast.LENGTH_SHORT).show();
	}

	public void startMedia() {
		if(app.isMediaRunning) {
			woot = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] vibro = {0, 300, 500};
			woot.vibrate(vibro, 0);
			
			Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if(alert == null){
				alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
				if(alert == null){  
					alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
				}
			}
			
			mp = new MediaPlayer();
		    try {
		    	mp.setDataSource("sdcard/zedge/ringtones/ÈikenKillerAlarm.mp3");
		    	mp.setLooping(true);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		    try {
		    	mp.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mp.start();
		}
	}
	
	Runnable mTask = new Runnable() {
		public void run() {
			
			startMedia();
		      
	        // show the icon in the status bar
	        showNotification();
			
            boolean isRunning = true;
            
            while (isRunning) {
                synchronized (mBinder) {
                    try {
                    	if(app.isMediaRunning) {
                    		mBinder.wait(2); //time millis
//                    		mBinder.notify();
                    	}
                    	else {
                    		isRunning = false;
                    	}
                    } 
                    catch (Exception e) {
                    }
                }
            }
            AlarmService.this.stopSelf();
        }
    };
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
//		return null;
	}
	
	private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.alarm_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.stat_sample, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Ring.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, app.getName(),
                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.alarm_service_started, notification);
    }

	private final IBinder mBinder = new Binder() {
        @Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,
		        int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };
}