/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:       gpsLocationService.java

 * DESCRIPTION:     gps location for future needs (locating the map in the world).
   				  that's a really shitty method, need to be improved immediatly !!!!!
 *				
 * Copyright 2014 Gilboa Shveki 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
