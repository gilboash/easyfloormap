/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       matrix_cordinates.java

  DESCRIPTION:    Matrix class.
  				  That's what happends when a C developer trying to write an android APP in java, and cant find STRUCTS!!!!!!

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/



package iScans.wifiscans;

import android.util.Log;
public class matrix_cordinates {
	private static final String TAG = "matrix_cordinates";
	public int left_x_border=0;
	public int right_x_border=0;
	public int high_y_border=0;
	public int low_y_border=0;

//	public String[] ap_ssid;
//	public int[] 	ap_rssi;
	
	public final int ACCESS_POINT_CELL_MAX = 10;

	

	
	public matrix_cordinates()
	{
//		ap_ssid = new String[ACCESS_POINT_CELL_MAX+1];
//		ap_rssi = new int[ACCESS_POINT_CELL_MAX+1];
//
//		for (int i=0;i<=ACCESS_POINT_CELL_MAX; i++)
//		{
//			ap_ssid[i] = "-";
//			ap_rssi[i] = 0;
//		}
	}
	
//	void set_accesspoint_params(String ssid, int rssi, int index)
//	{
//		//Write it to a file also and export it
//		if (index>ACCESS_POINT_CELL_MAX)
//		{
//			Log.d(TAG,"WARNING - set_accesspoint_params, index "+index+" is higer then allowed "+ACCESS_POINT_CELL_MAX);
//			return;
//		}
//		ap_ssid[index] = ssid;
//		ap_rssi[index] = rssi;
//		
//	}
	
	
   void set_left_x_border(int value)
   {
	   left_x_border=value;
   }
   
   void set_right_x_border(int value)
   {
	   right_x_border=value;
   }
   void set_high_y_border(int value)
   {
	   high_y_border=value;
   }
   void set_low_y_border(int value)
   {
	   low_y_border=value;
   }
	   

	
	
   int get_left_x_border()
   {
	   return left_x_border;
   }
   
   int get_right_x_border()
   {
	   return right_x_border;
   }
   int get_high_y_border()
   {
	   return high_y_border;
   }
   int get_low_y_border()
   {
	   return low_y_border;
   }
   

	
}
