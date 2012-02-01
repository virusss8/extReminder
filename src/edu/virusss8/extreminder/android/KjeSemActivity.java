package edu.virusss8.extreminder.android;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class KjeSemActivity extends MapActivity {
	
	MapController mapController;
	MyPositionOverlay positionOverlay;
	Aplikacija app;

	@Override   
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = (Aplikacija) getApplication();
		setContentView(R.layout.maps);

		MapView myMapView = (MapView)findViewById(R.id.myMapView);
		mapController = myMapView.getController();

//		myMapView.setSatellite(true);
//		myMapView.setStreetView(true);
//		myMapView.displayZoomControls(false);
		myMapView.setSatellite(false);
		myMapView.setStreetView(false);
		myMapView.displayZoomControls(true);
		myMapView.setTraffic(false);

		mapController.setZoom(17);

		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		// Add the MyPositionOverlay
		positionOverlay = new MyPositionOverlay();
		List<Overlay> overlays = myMapView.getOverlays();
		overlays.add(positionOverlay);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);

		Location location = locationManager.getLastKnownLocation(provider);
		if (location!=null)
		  my_updateWithNewLocation(location);

		locationManager.requestLocationUpdates(provider, 2000, 10,   
				locationListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			my_updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider){
			my_updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider){ }
		public void onStatusChanged(String provider, int status, 
				Bundle extras){ }
	};

	private void my_updateWithNewLocation(Location location) {
		String latLongString;
		TextView myLocationText;
		myLocationText = (TextView)findViewById(R.id.myLocationText);

		if (location != null) {
			positionOverlay.setLocation(location);

			Double geoLat = location.getLatitude()*1E6;
			Double geoLng = location.getLongitude()*1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(), 
					geoLng.intValue());

			mapController.animateTo(point);

			double lat = location.getLatitude();
			double lng = location.getLongitude();
			
			latLongString = "Long:" + lng+"\n"+"Lat:" + lat ;

			myLocationText.setText(latLongString); 
			
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
					Uri.parse("http://maps.google.com/maps?saddr="+lat+","+lng+"&daddr=" + app.getLokacijaLekarne()));
			startActivity(intent);
		}
	}
}
