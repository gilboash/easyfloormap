/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:        rssi_cell_rank_list.java

 * DESCRIPTION:    ranking cells for their RSSI per AP - this is the real deal !!!
  				  learning mode - per AP insert cell and sort rssi 
  				  locating mode - find closest cell for this AP according to its RSSI
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

import java.util.List;
import android.util.Log;
import android.net.wifi.ScanResult;

public class rssi_cell_rank_list {
	public Boolean configured;
	private Defines gDefines;

	//public int[] learned_cell_index;
	public int[] cell_index_rssi_rank_list;
	public int[] rssi_value_list;
	private static final String TAG = "IndoorPlaceMe:rssi_cell_rank_list";
	
	public rssi_cell_rank_list(Defines gdefs)
	{
		configured=false;
		gDefines = gdefs;
		cell_index_rssi_rank_list = new int[gDefines.MAX_CELL_INDEX_LIST_FOR_AP];
		rssi_value_list			  = new int[gDefines.MAX_CELL_INDEX_LIST_FOR_AP];
		for (int i=0;i<gDefines.MAX_CELL_INDEX_LIST_FOR_AP;i++)
		{
			cell_index_rssi_rank_list[i] = -1;
			rssi_value_list[i]			 = -200;
		}
//		learned_cell_index = new int[gDefines.get_maximum_cell_index()];
//		for (int j=0;j<gDefines.get_maximum_cell_index();j++)
//		{
//			learned_cell_index[j]=0;
//		}
	}
	
	
	public void insert_and_sort_cell_index(int cell_index, int rssi_bar_value)
	{
		configured=true;
		
//		if (learned_cell_index[cell_index]!=0)
//		{
//			Log.d(TAG,"insert_and_sort_cell_index, cell index already learned "+cell_index);
//			return;
//		}
//		//Else
//		learned_cell_index[cell_index]=1;
		
		//if cell already configured, and cell is known for this AP - first remove it from the lists
		if (gDefines.is_cell_configured(cell_index))
		{
			for (int index=0;index<gDefines.MAX_CELL_INDEX_LIST_FOR_AP;index++)
			{
				if (cell_index_rssi_rank_list[index] == cell_index)
				{
					System.arraycopy(rssi_value_list,index+1, rssi_value_list, index, rssi_value_list.length - index -1);
					System.arraycopy(cell_index_rssi_rank_list,index+1, cell_index_rssi_rank_list, index, cell_index_rssi_rank_list.length - index -1);
				}
			}
		}
		
		//Now sort and insert the values to the right location
		for ( int index=0;index<gDefines.MAX_CELL_INDEX_LIST_FOR_AP;index++)
		{
			if (rssi_value_list[index]<rssi_bar_value)
			{
				System.arraycopy(rssi_value_list,index, rssi_value_list, index+1, rssi_value_list.length - index - 1);
				System.arraycopy(cell_index_rssi_rank_list,index, cell_index_rssi_rank_list, index+1, cell_index_rssi_rank_list.length - index - 1);
				rssi_value_list[index] = rssi_bar_value;
				cell_index_rssi_rank_list[index] = cell_index;
				return;
			}
		}
	}
	
	
	
	public int find_closest_rssi_and_grade_cell(int current_rssi_val_bar)
	{

		if (!configured)
			return -1;
		
		for (int index=0;index<gDefines.MAX_CELL_INDEX_LIST_FOR_AP;index++)
		{
			if (rssi_value_list[index]==-200)
			{
				
				//Wait - before exiting, shouldnt we grade the cells that did not see this AP at all?
				//		 meaning RSSI_Level = 0 for them for this AP
				//		Lets give it a try

				//      or.. maybe not
				
				
				//it means i got lower value then all of those in the list, return the last one checked
				return cell_index_rssi_rank_list[index-1];
			}
			
			double grade = Math.pow((rssi_value_list[index] - current_rssi_val_bar),2);
			
			gDefines.grade_cell_index_for_val_diff(cell_index_rssi_rank_list[index],grade);
			
//			if (rssi_value_list[index]<current_rssi_val_bar)
//			{
//				//if index is 0, then it means im closest to the first value sampled - return first cell_index in array
//				//if not, and i entered here, it means my rssi value is fallen between 2 rssi value sampled
//				//so i need to decide which one im closer to
//
//				// for example, rssi value list:
//				//(rssi=19,cell_index=32) -> (rssi=17,cell_index=42) -> (rssi=13,cell_index=75)
//				// i'll ask if my current_rssi > rssi list index, and if it is ill check if im closer to the current index, or the previous (index-1)
//				// 1- if my rssi value is 14, my closest value is rssi 13, so ill return cell_index of current index=75
//				// 2- if my rssi value is 16, my closest value is rssi 17, so ill return cell_index of previous index = 42  
//				
//				if (index==0 || ((current_rssi_val_bar-rssi_value_list[index])<(rssi_value_list[index-1]-current_rssi_val_bar)))
//				{
//					return cell_index_rssi_rank_list[index];
//				}
//				//Else
//				return cell_index_rssi_rank_list[index-1];
//			}
		}
		return 1;
	}
	
}
