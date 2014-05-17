/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       gpsLocationService.java

  DESCRIPTION:    gps location for future needs (locating the map in the world).
   				  that's a really shitty method, need to be improved immediatly !!!!!

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/


package iScans.wifiscans;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class gpsLocationService implements LocationListener {

	MainActivity _main;
	public gpsLocationService(MainActivity __main)
	{
		_main = __main;
	}
	
	@Override
	public void onLocationChanged(Location loc)
	{

		loc.getLatitude();
		loc.getLongitude();

	    String Text = "My current location is: " +	"Latitud = " + loc.getLatitude() +	"Longitud = " + loc.getLongitude();


		Toast.makeText( _main,
			Text,		
			Toast.LENGTH_SHORT).show();

	}


	@Override
	public void onProviderDisabled(String provider)
	{

		Toast.makeText( _main,
			"Gps Disabled",
			Toast.LENGTH_SHORT ).show();
	
	}


	@Override
	public void onProviderEnabled(String provider)
	{
		Toast.makeText( _main,
			"Gps Enabled",
			Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{

	}
}
