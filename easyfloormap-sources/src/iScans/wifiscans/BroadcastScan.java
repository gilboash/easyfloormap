/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       BroadcastScan.java

  DESCRIPTION:    scan result broadcast reciever, engine of the wifi scan results
  				  sort and fill database/grade cells

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/


package iScans.wifiscans;


import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class BroadcastScan extends BroadcastReceiver {
	  private static final String TAG = "IndoorPlaceMe:WiFiScanReceiver";
	  
	  public int allocated_rssi_levels_bitmap;
	  private MainActivity wifiDemo;
	  private Defines gDefines;
	  private ssid_rssi_prms ssidRssiParams;

	  
	  public BroadcastScan(MainActivity wifiDemo,Defines defineVariables,ssid_rssi_prms sRprms) {
		    super();
		    this.wifiDemo = wifiDemo;
		    gDefines = defineVariables;
		    allocated_rssi_levels_bitmap = 0;
		    ssidRssiParams = sRprms;

    		Log.d(TAG,"BroadCast Scan activity created");
	  }
	  @Override
	  public void onReceive(Context c, Intent intent) {
	    List<ScanResult> results = wifiDemo.wifiManager.getScanResults();
	    //ScanResult bestSignal = null;
	    //wifiDemo.writeToEditText("Results:\n",false);
	    
	    Log.d(TAG,"BroadCast Recieved event");
	    if (gDefines.gTrackRemoteDevice)//tracking remote device, not the one you are running this app on !
	    	return;

	    //else 
	    if (gDefines.gLearningMode == false)
	    {
	    	gDefines.init_cell_index_grades_array();	
    		ssidRssiParams.init_graded_ap_ssid();
	    }
	    int index=0;
	    String[] ssid_arr = new String[gDefines.ACCESS_POINT_TO_GRADE_MAX];//{"","","","",""};
	    int[] ssid_rssi_val = new int[gDefines.ACCESS_POINT_TO_GRADE_MAX];//{0,0,0,0,0};
	    for (int i=0;i<gDefines.ACCESS_POINT_TO_GRADE_MAX; i++)
	    {
	    	ssid_arr[i]="";
	    	ssid_rssi_val[i]=-200;
	    }
	    
	    
	    for (ScanResult result : results) {
	    	
//	    	if (bestSignal == null
//	          || WifiManager.compareSignalLevel(bestSignal.level, result.level) < 0)
//	    				bestSignal = result;
	    	
	    	//DEBUG DEBUG DEBUG
//	    	if (!(result.SSID.contains("GilboaMeital")||result.SSID.contains("ghnet")||result.SSID.contains("Zohar")))
//	    		continue;
	    		
	    		
	    	int rssi_val_bar = result.level;//WifiManager.calculateSignalLevel(result.level, gDefines.RSSI_LEVELS);
	    	
	    	
//	    	//Allign RSSI to reasonable boundaries (some ap's go wild in the nature and sometimes send -11dBm beacon/probe, this is crazy)
//			if (rssi_val_bar > gDefines.RSSI_MAX_LEVEL)
//			{
//				Log.d(TAG,"Scan result allignement-TOO HIGH, SSID "+result.SSID+ " original RSSI level "+ rssi_val_bar + " alligned to "+gDefines.RSSI_MAX_LEVEL);
//				rssi_val_bar = gDefines.RSSI_MAX_LEVEL;
//				
//			}else if (rssi_val_bar < gDefines.RSSI_MIN_LEVEL)
//			{
//				Log.d(TAG,"Scan result allignement-TOO LOW, SSID "+result.SSID+ " original RSSI level "+ rssi_val_bar + " alligned to "+gDefines.RSSI_MIN_LEVEL);
//				rssi_val_bar = gDefines.RSSI_MIN_LEVEL;
//				
//			}
		

//DEBUG - 	    	Log.d(TAG,"Scan result DEBUG SSID "+result.SSID+ " RSSI level "+ rssi_val_bar);
    		
	    	//Toast.makeText(wifiDemo, "ssid "+result.SSID+" rssi level "+ Math.abs(result.level)+" MAC "+result.BSSID, Toast.LENGTH_SHORT).show();
	    	if (gDefines.gLearningMode == true)
	    	{
	    		if (gDefines.matrix_location_learning_requested == true)
	    		{
	    			

	    			//Some AP's go wild and sometimes shoot very high power beacon/probe
		    		if (rssi_val_bar > gDefines.RSSI_MAX_LEVEL)
		    		{
		    			Log.d(TAG,"Learning - RSSI too high for SSID " + result.SSID + "="+rssi_val_bar+" -dont sort it...");
		    			continue;
		    		}
	    			
		    		//Allocate ssid index for this ssid
	    			
	    			// Gilboa 9/4/13 - map according to MAC, it's more unique
		    		int ssid_index = ssidRssiParams.allocate_access_point_ssid_index(result.BSSID,result.SSID);
		    		
		    		
		    		//allocated_rssi_levels_bitmap |= BIT_x(WifiManager.calculateSignalLevel(result.level, 20));
		    		
		    		int cell_index = gDefines.cell_i_j_to_index(gDefines.curr_matrix_x, gDefines.curr_matrix_y);
		    		if (ssid_index!= -1)
		    		{
		    			ssidRssiParams.sort_rssi_for_cell(cell_index, ssid_index, rssi_val_bar);
		    			Log.d(TAG," Learning mode: recieved rssi:"+rssi_val_bar+" from ssid "+result.SSID+" (ssid index "+ssid_index+") on cell index "+cell_index);
		    		}
		    		else
		    		{
		    			Log.e(TAG," ap ssid list is full!!! no more room");
		    			Toast.makeText(wifiDemo, "ap ssid list is full!!", Toast.LENGTH_SHORT).show();
		    		}
		    		
		    		
		    		
	    		}
	    	}
	    	else//Locate me upon this point sample only
	    	{
	    		//Some AP's go wild and sometimes shoot very high power beacon/probe
	    		if (rssi_val_bar > gDefines.RSSI_MAX_LEVEL)
	    			continue;
	    		
	    		if (rssi_val_bar > ssid_rssi_val[gDefines.ACCESS_POINT_TO_GRADE_MAX - 1])
	    		{
	    		
		    		for (int j=0;j<gDefines.ACCESS_POINT_TO_GRADE_MAX; j++)
		    		{
		    			if (rssi_val_bar > ssid_rssi_val[j])
		    			{
		    				//int tmp = ssid_rssi_val[j];
		    				//String tmp_str = ssid_arr[j];
		    				
		    				System.arraycopy(ssid_rssi_val,j, ssid_rssi_val, j+1, gDefines.ACCESS_POINT_TO_GRADE_MAX - j -1);
		    				System.arraycopy(ssid_arr,j, ssid_arr, j+1, gDefines.ACCESS_POINT_TO_GRADE_MAX - j -1);
		    				
		    				ssid_rssi_val[j] = rssi_val_bar;
		    				ssid_arr[j] = result.BSSID;  
		    				index++;
		    				break;
		    			}
		    		}
	    		}
	    		

//	    		Log.d(TAG,"Locate me event SSID "+result.SSID+" rssi value bar "+rssi_val_bar);
//	    		ssidRssiParams.grade_ssid_rssi(result.SSID,rssi_val_bar);
	    	}
    	}

	    //String message = String.format("%s networks found. %s is the strongest.",
	    //    results.size(), bestSignal.SSID);
	    String message="Nothing is done..";
	    if (gDefines.gLearningMode == true)
    	{
	    	if (gDefines.matrix_location_learning_requested==true)
	    	{
		    	message = String.format("%s networks learned for this spot. Move on to the next cell",
		    		        		results.size());
		    	Toast.makeText(wifiDemo, message, Toast.LENGTH_SHORT).show();
		    	Log.d(TAG,message);		    	
	    		//Mark as learned
	    		gDefines.matrix_location_learning_requested = false;
	    	}
    	}
	    else
	    {
//    		//grade the mother fu#%@er
	    	
	    	Log.d(TAG,"TESTING THE MAXIMUM "+index+" SSIDs: ");
	    	for (int i=0;i<gDefines.ACCESS_POINT_TO_GRADE_MAX && ssid_rssi_val[i]!=-200; i++)
	    	{
	    		
	    		//Log.d(TAG,"index "+i+" ssid_arr" + ssid_arr[i]+ " ssid_rssi_val "+ssid_rssi_val[i]);
	    		
	    		Log.d(TAG,"Locate me event SSID "+ssid_arr[i]+" rssi value bar "+ssid_rssi_val[i]);
	    		ssidRssiParams.grade_ssid_rssi(ssid_arr[i],ssid_rssi_val[i]);
	    		
	    	}
	    	

	    	
	    	
	    	//Locate me mode
	    	
		    //Look for highest grade
	    	int located_cell_index = find_best_cell_grade(/*current cell*/gDefines.curr_matrix_x, gDefines.curr_matrix_y);
	    	if (located_cell_index!=-1)
	    	{
		    	int i= gDefines.cell_index_to_i(located_cell_index);
		    	int j= gDefines.cell_index_to_j(located_cell_index);
	
		    	//Set the new location as to where am i
	    		gDefines.setNewMatrix_X_and_Y(i, j);
	    		wifiDemo.external_post_invalidate();
	    		message = String.format("located best grade cell index is %d which is x-%d and y-%d",located_cell_index,i,j);
	    	}
	    	else
	    	{
	    		message = String.format("no cell was located !");
	    		Log.e(TAG, "no cell was located !");
	    	}
	    }
	    
	    Log.d(TAG, "onReceive() message: " + message);
	    
	  }

	  
	  
	  public void trackRemoteDeviceMsgReceived(String msg)
	  {
		  
			  //int[] rssi_array 	   = new int[MAX_TRACKED_SCAN_ENTRIES];
			  //String[] bssid_array = new String[MAX_TRACKED_SCAN_ENTRIES];
			  //int index = 0;
		    
		    Log.d(TAG,"Remote Tracking scan result event received");
		    if (!gDefines.gTrackRemoteDevice)//tracking remote device mode
		    	return;
		    
		     
		    if (gDefines.gLearningMode == false)
		    {
		    	gDefines.init_cell_index_grades_array();	
	    		ssidRssiParams.init_graded_ap_ssid();
		    }
		    
		    int index = 0;
		    String[] ssid_arr = new String[gDefines.ACCESS_POINT_TO_GRADE_MAX];//{"","","","",""};
		    int[] ssid_rssi_val = new int[gDefines.ACCESS_POINT_TO_GRADE_MAX];//{0,0,0,0,0};
		    for (int i=0;i<gDefines.ACCESS_POINT_TO_GRADE_MAX; i++)
		    {
		    	ssid_arr[i]="";
		    	ssid_rssi_val[i]=-200;
		    }
		    
		    //parse message into list of RSSI and BSSIDs
			String[] seperated1 = msg.split("SCAN_REPORT:");
			String[] seperated2 = seperated1[1].split("M:");
			int result_count=0;
			
			for (String full_result : seperated2) {
				if (!full_result.contains("R:"))
					continue;
				String[] splitted_result = full_result.split("R:");
				String bssid = splitted_result[0];
				
				if (splitted_result[1].contains("\n"))
					continue;
				
				int rssi = (-1)*Integer.valueOf(splitted_result[1]);
		    	
				if (bssid=="0:0:0:0:0:0"||rssi==0)
					continue;
				result_count++;
		    	int rssi_val_bar = rssi;//WifiManager.calculateSignalLevel(result.level, gDefines.RSSI_LEVELS);
		    	

		    	//Toast.makeText(wifiDemo, "ssid "+result.SSID+" rssi level "+ Math.abs(result.level)+" MAC "+result.BSSID, Toast.LENGTH_SHORT).show();
		    	if (gDefines.gLearningMode == true)
		    	{
		    		if (gDefines.matrix_location_learning_requested == true)
		    		{
		    			

		    			//Some AP's go wild and sometimes shoot very high power beacon/probe
			    		if (rssi_val_bar > gDefines.RSSI_MAX_LEVEL)
			    		{
			    			//Log.d(TAG,"Learning - RSSI too high for SSID " + result.SSID + "="+rssi_val_bar+" -dont sort it...");
			    			continue;
			    		}
		    			
			    		//Allocate ssid index for this ssid
		    			
		    			// Gilboa 9/4/13 - map according to MAC, it's more unique
			    		int ssid_index = ssidRssiParams.allocate_access_point_ssid_index(bssid,"tracked_device_ap");
			    		
			    		
			    		//allocated_rssi_levels_bitmap |= BIT_x(WifiManager.calculateSignalLevel(result.level, 20));
			    		
			    		int cell_index = gDefines.cell_i_j_to_index(gDefines.curr_matrix_x, gDefines.curr_matrix_y);
			    		if (ssid_index!= -1)
			    		{
			    			ssidRssiParams.sort_rssi_for_cell(cell_index, ssid_index, rssi_val_bar);
			    			Log.d(TAG," Learning mode: recieved rssi:"+rssi_val_bar+" from ssid "+"tracked_device_ap"+" (ssid index "+ssid_index+") on cell index "+cell_index);
			    		}
			    		else
			    		{
			    			Log.e(TAG," ap ssid list is full!!! no more room");
			    			Toast.makeText(wifiDemo, "ap ssid list is full!!", Toast.LENGTH_SHORT).show();
			    		}
			    		
			    		
			    		
		    		}
		    	}
		    	else//Locate me upon this point sample only
		    	{
		    		//Some AP's go wild and sometimes shoot very high power beacon/probe
		    		if (rssi_val_bar > gDefines.RSSI_MAX_LEVEL)
		    			continue;
		    		
		    		//Grade only highest 12 rssi's result, throw the others
		    		if (rssi_val_bar > ssid_rssi_val[gDefines.ACCESS_POINT_TO_GRADE_MAX - 1])
		    		{
		    		
		    			//Sort rssi list (rssi value list and ssid list)
			    		for (int j=0;j<gDefines.ACCESS_POINT_TO_GRADE_MAX; j++)
			    		{
			    			if (rssi_val_bar > ssid_rssi_val[j])
			    			{
			    				//int tmp = ssid_rssi_val[j];
			    				//String tmp_str = ssid_arr[j];
			    				
			    				System.arraycopy(ssid_rssi_val,j, ssid_rssi_val, j+1, gDefines.ACCESS_POINT_TO_GRADE_MAX - j -1);
			    				System.arraycopy(ssid_arr,j, ssid_arr, j+1, gDefines.ACCESS_POINT_TO_GRADE_MAX - j -1);
			    				
			    				ssid_rssi_val[j] = rssi_val_bar;
			    				ssid_arr[j] = bssid;  
			    				index++;
			    				break;
			    			}
			    		}
		    		}
		    		

//		    		Log.d(TAG,"Locate me event SSID "+result.SSID+" rssi value bar "+rssi_val_bar);
//		    		ssidRssiParams.grade_ssid_rssi(result.SSID,rssi_val_bar);
		    	}
	    	}

		    //String message = String.format("%s networks found. %s is the strongest.",
		    //    results.size(), bestSignal.SSID);
		    String message="Nothing is done..";
		    if (gDefines.gLearningMode == true)
	    	{
		    	if (gDefines.matrix_location_learning_requested==true)
		    	{
			    	message = String.format("%s networks learned for this spot. Move on to the next cell",
			    			result_count);
			    	Toast.makeText(wifiDemo, message, Toast.LENGTH_SHORT).show();
			    	Log.d(TAG,message);		    	
		    		//Mark as learned
		    		gDefines.matrix_location_learning_requested = false;
		    	}
	    	}
		    else
		    {
//	    		//grade the mother fu#%@er
		    	
		    	Log.d(TAG,"TESTING THE MAXIMUM "+index+" SSIDs: ");
		    	for (int i=0;i<gDefines.ACCESS_POINT_TO_GRADE_MAX && ssid_rssi_val[i]!=-200; i++)
		    	{
		    		
		    		//Log.d(TAG,"index "+i+" ssid_arr" + ssid_arr[i]+ " ssid_rssi_val "+ssid_rssi_val[i]);
		    		
		    		Log.d(TAG,"Locate me event SSID "+ssid_arr[i]+" rssi value bar "+ssid_rssi_val[i]);
		    		ssidRssiParams.grade_ssid_rssi(ssid_arr[i],ssid_rssi_val[i]);
		    		
		    	}
		    	

		    	
		    	
		    	//Locate me mode
		    	
			    //Look for highest grade
		    	int located_cell_index = find_best_cell_grade(/*current cell*/gDefines.curr_matrix_x, gDefines.curr_matrix_y);
		    	if (located_cell_index!=-1)
		    	{
			    	int i= gDefines.cell_index_to_i(located_cell_index);
			    	int j= gDefines.cell_index_to_j(located_cell_index);
		
			    	//Set the new location as to where am i
		    		gDefines.setNewMatrix_X_and_Y(i, j);
		    		wifiDemo.external_post_invalidate();
		    		message = String.format("located best grade cell index is %d which is x-%d and y-%d",located_cell_index,i,j);
		    	}
		    	else
		    	{
		    		message = String.format("no cell was located !");
		    		Log.e(TAG, "no cell was located !");
		    	}
		    }
		    
		    Log.d(TAG, "onReceive() message: " + message);
		    
	  }
	  
	  
	  private boolean is_constrained_search_space(int cell_index_to_check, int current_x,int current_y)
	  {
		  int x= gDefines.cell_index_to_i(cell_index_to_check);
	      int y= gDefines.cell_index_to_j(cell_index_to_check);
	      
	      return  (y >= current_y - gDefines.CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS &&
	    		   y <= current_y + gDefines.CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS &&
	    		   x >= current_x - gDefines.CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS &&
	    		   x <= current_x + gDefines.CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS) ||
	    		   (current_x == -1) || 
	    		   (current_y == -1);
		  
	  }
	  
	  //look for highest grade in cell_index_grades
	  private int find_best_cell_grade(int current_xis,int current_yis)
	  {
		  float min_grade=999999;
		  int 	 best_cell_index=-1;
		  for (int j=0;j<gDefines.get_maximum_cell_index();j++)
		  {
			  if ((gDefines.cell_index_configured[j]==0) || (gDefines.cell_index_is_graded[j]==0) || !(is_constrained_search_space(j,current_xis,current_yis)))
				  continue;
			  //Calculate grade relative to the amount of graders
			  float curr1 = (float) Math.sqrt(gDefines.cell_index_grades[j]);
			  float curr2 = gDefines.cell_index_is_graded[j];
			  float current_cell_grade= curr1 / curr2;
			  if (min_grade > current_cell_grade)
			  {
				  min_grade = current_cell_grade;
				  best_cell_index = j;
			  }
		  }
	      Log.d(TAG,"find best cell grade "+min_grade + " which is in cell index " +best_cell_index);
		  return best_cell_index;
	  }
	  
//	  private int BIT_x(int x)
//	  {
//		  return (1 << (x));
//	  }
//	  
//	  private int find_lowest_bit(int bitmap)
//	  {
//	    	return Integer.lowestOneBit(bitmap);
//	  }
	  
//	  private long time;
//
//	  private void startBenchmark() {  
//		  time = System.currentTimeMillis();
//      }
//		  
//		        
//	  private void stopBenchmark() {  
//		  time = System.currentTimeMillis() - time;
//		  Log.d("Benchmark time measurements: ", "time="+time);
//      }

	  
	  
}
