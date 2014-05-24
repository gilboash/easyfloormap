/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       ssid_rssi_prms.java

  DESCRIPTION:    rssi grading module - this is the real deal !!!
  				  Gathering and sorting rssi values from all AP's per cell
  				  for learning mode - store in DB (sorted)
  				  for mapping - decide which cell is the closest according to all RSSI values

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/



package iScans.wifiscans;

import android.os.Environment;
import android.util.Log;



public class ssid_rssi_prms {

private Defines gDefines;
	private static final String TAG = "IndoorPlaceMe:SSID_RSSI_Operations";
	public String[] ap_mac_to_index;
	public String[] ap_ssid_to_index;
	//public int[] ap_mac_black_list;
	public int[] ap_ssid_graded;
	
	public rssi_cell_rank_list[] rssi_cell_rankList;
	private MapView gMapView;
	//public String gMapFileLoc="";
	public ssid_rssi_prms(Defines gDefs)
	{
		gDefines = gDefs;
		ap_ssid_graded	 = new int[gDefines.ACCESS_POINT_MAX];
		init_graded_ap_ssid();
		init_ssid_rssi_prms();
		
	}
	
	//Init all current params
	public void init_ssid_rssi_prms()
	{
		ap_mac_to_index = new String[gDefines.ACCESS_POINT_MAX];
		ap_ssid_to_index = new String[gDefines.ACCESS_POINT_MAX];
		
		rssi_cell_rankList = new rssi_cell_rank_list[gDefines.ACCESS_POINT_MAX];
		for (int k=0;k<gDefines.ACCESS_POINT_MAX;k++)
		{
			ap_mac_to_index[k]="-";
			ap_ssid_to_index[k]="-";
			rssi_cell_rankList[k] = new rssi_cell_rank_list(gDefines);
		}
		gDefines.init_cell_index_configured_array();
		
	}
	
	public void init_graded_ap_ssid()
	{
		for (int k=0;k<gDefines.ACCESS_POINT_MAX;k++)
		{
			ap_ssid_graded[k]=0;
		}
	}
	
	public void set_map_view_when_u_can(MapView the_real_mapview)
	{
		gMapView = the_real_mapview;
	}
	public void sort_rssi_for_cell(int cell_index, int ap_ssid_index, int rssi_val_bar)
	{
		
		
		if (ap_mac_to_index[ap_ssid_index].compareTo("-")!=0)
		{//ssid exist
			Log.d(TAG,"sort_rssi_for_cell: Ssid index "+ap_ssid_index+" cell index "+cell_index+" rssi val bar "+rssi_val_bar);
			rssi_cell_rankList[ap_ssid_index].insert_and_sort_cell_index(cell_index, rssi_val_bar);
			//Mark this cell as configured
			gDefines.mark_cell_configured(cell_index);
			return;
		}
		Log.e(TAG,"sort_rssi_for_cell: Ssid index doesn't have entry in ssid table (index="+ap_ssid_index+")");
		//ASSERT
	}
	
	public int allocate_access_point_ssid_index(String MAC, String ssid)
	{
		
		for (int i=0;i<gDefines.ACCESS_POINT_MAX; i++)
		{
			//Log.d(TAG,"allocate ap ssid index ssid="+ssid+" allocated with index "+i);
			//Already exist
			//int result = ap_mac_to_index[i].compareTo(ssid);
			if ((ap_mac_to_index[i].compareTo(MAC))==0)
			{
				Log.d(TAG,"allocate ap ssid index already exist, ssid="+ssid+" MAC " +MAC +" on index "+i);
				return i;
			}
			//Else - add it as last index
			if ((ap_mac_to_index[i].compareTo("-"))==0)
			{
				ap_mac_to_index[i]=MAC;
				ap_ssid_to_index[i]=ssid;
				Log.d(TAG,"allocate ap ssid index ssid="+ssid+" and MAC "+MAC+ " allocated with index "+i);				
				return i;
			}
		}
		Log.e(TAG,"allocate ap ssid "+ssid+" MAC "+MAC+" failed, list is full");
		return -1;
	}
	
	public int get_access_point_ssid_index(String MAC)
	{
		for (int i=0;i<gDefines.ACCESS_POINT_MAX; i++)
		{
			if (ap_mac_to_index[i].compareTo(MAC)==0)
				return i;
		}
		return -1;
	}
	
	
	
	
	
	//Locate me mode functions
//	private int find_closest_cell_index_for_ssid_index(int ap_ssid_index,int current_rssi_val_bar)
//	{
//		return rssi_cell_rankList[ap_ssid_index].find_closest_rssi_and_return_cell(current_rssi_val_bar);
//	}
	
	private int find_closest_cell_index_and_grade_ssid(int ap_ssid_index,int current_rssi_val_bar)
	{
		return rssi_cell_rankList[ap_ssid_index].find_closest_rssi_and_grade_cell(current_rssi_val_bar);
	}
	
	public void grade_ssid_rssi(String MAC, int current_rssi_val_bar)
	{
		int ap_ssid_index = get_access_point_ssid_index(MAC);
		if (ap_ssid_index==-1)
		{
			Log.d(TAG," SSID index not found - MAC "+MAC);
			return;
		}
		
		//Avoid grading the same SSID twice
		if (ap_ssid_graded[ap_ssid_index]==1)
		{
			Log.d(TAG," SSID already graded- MAC "+MAC);
			return;
		}
			
		
		ap_ssid_graded[ap_ssid_index]=1;
		//make array in broadcast activity (initted in "Locate me mode" first ssid)
		//fill in a huge array with grades and stuff here
		//call find_closest_cell_index_for_ssid, and add to the cell_index it returns the current_rssi_val_bar
		
		//int closest_cell_index = find_closest_cell_index_for_ssid_index(ap_ssid_index, current_rssi_val_bar);
		int result = find_closest_cell_index_and_grade_ssid(ap_ssid_index, current_rssi_val_bar);
		
		if (result==-1)
		{
			Log.e(TAG,"grade_this_damn_ssid:failed to grade cell index for ap ssid "+ap_ssid_index);
			return;
		}
		Log.d(TAG,"grade_this_damn_ssid: ap ssid index "+ap_ssid_index+" last cell index was "+result);
		return;
	}
	
//	public void save_map_image_file_path(String file_path)
//	{
//		gMapFileLoc = file_path;
//
//	}
	
		
	public void save_current_rssi_mapping_to_file(FileOutputInput fileStream,String filenameforimage,double latitude,double long_latitude)
	{
		String body="==##==WifiLocatorConfigurationFile==##==\n";
		//New - Set matrix size and version
		body+="Matrix Size:\n";
		body+=gDefines.X_CELL_AMOUNT+"\n";
		body+=gDefines.Y_CELL_AMOUNT+"\n";
		
		//read ap to ssid mapping
		body+="ap_mac_to_index:\n";
		
		for (int i=0;i<(gDefines.ACCESS_POINT_MAX); i++)
		{
			body+=ap_mac_to_index[i]+"\n";
			body+=ap_ssid_to_index[i]+"\n";
		}
		
		body+="rssi_cell_rankList:\n";
		
		for (int i=0;i<gDefines.ACCESS_POINT_MAX; i++)
		{
			body+=rssi_cell_rankList[i].configured+"\n";
			for (int index=0;index<gDefines.MAX_CELL_INDEX_LIST_FOR_AP;index++)
			{
				body+=rssi_cell_rankList[i].cell_index_rssi_rank_list[index]+"\n";
				body+=rssi_cell_rankList[i].rssi_value_list[index]+"\n";
				
				if (rssi_cell_rankList[i].cell_index_rssi_rank_list[index]==-1)
				{
					break;
				}
			}
//			for (int j=0;j<gDefines.get_maximum_cell_index();j++)
//			{
//				body+=rssi_cell_rankList[i].learned_cell_index[j]+"\n";
//			}
			
		}
		
		//Save the picture locally now
		String mapFileLoc= gMapView.save_bitmap_image_locally(filenameforimage+".jpg");
		body+="Map_File_Attached:\n"+mapFileLoc+"\n";
		body+="Latitue:\n"+latitude+"\n";
		body+="LongLatitue:\n"+long_latitude+"\n";
		fileStream.write_to_file(body);
	}
	
	public Boolean load_rssi_mapping_from_file(FileOutputInput fileStream)
	{
		String read_body = fileStream.read_from_file();
		String[] seperated = read_body.split("\n");

		if (seperated[0].compareTo("==##==WifiLocatorConfigurationFile==##==")!=0)
		{
			//not the kind of file we are looking for
			return false;
		}
		

		
		int offset=2;
		
		if (seperated[1].compareTo("Matrix Size:")==0)
		{
			//		==##==WifiLocatorConfigurationFile==##==
			//			Matrix Size:
			//			10
			//			7
			//			ap_mac_to_index:
			//			ssid1
			//			ssid2
			//			;
			//			;
			gDefines.init_matrix_values(Integer.parseInt(seperated[2]), Integer.parseInt(seperated[3]));
			
			offset=5;
		}
		else if (seperated[1].compareTo("ap_mac_to_index:")==0)
		{
			//		==##==WifiLocatorConfigurationFile==##==
			//			ap_mac_to_index:
			//			ssid1
			//			ssid2
			//			;
			//			;
			
			//Set default matrix
			
			gDefines.init_matrix_values(gDefines.X_CELL_AMOUNT_DFLT,gDefines.Y_CELL_AMOUNT_DFLT);
			
			offset=2;
		}
		else
			return false;

		
		//Init the ssid rssi params
		init_ssid_rssi_prms();
		
		int i=0,k=0;
		for (;i<(gDefines.ACCESS_POINT_MAX); i++,k=k+2)
		{
			ap_mac_to_index[i] = seperated[offset+k];
			ap_ssid_to_index[i] = seperated[offset+k+1];
		}
		i=k;
		if (seperated[offset+i].compareTo("rssi_cell_rankList:")!=0)
		{
			return false;
		}
		i+=(offset+1);//i is the offset
		int j=0;
		for (;j<gDefines.ACCESS_POINT_MAX; j++)
		{
			if (seperated[i].equals("true"))
			{
				rssi_cell_rankList[j].configured =  true;	
			}
			else
			{
				rssi_cell_rankList[j].configured =  false;
			}
			
			int index=0,idx=0;
			for (;idx<gDefines.MAX_CELL_INDEX_LIST_FOR_AP;index+=2,idx++)
			{
				if (Integer.parseInt(seperated[1+i+index])==-1)
				{
					index+=2;
					break;
					
				}
				rssi_cell_rankList[j].cell_index_rssi_rank_list[idx]=Integer.parseInt(seperated[1+i+index]);
				//Mark cell as configured
				gDefines.cell_index_configured[rssi_cell_rankList[j].cell_index_rssi_rank_list[idx]]=1;
				
				rssi_cell_rankList[j].rssi_value_list[idx]		  =Integer.parseInt(seperated[2+i+index]);
			}
			
//			int k=0;
//			for (;k<gDefines.get_maximum_cell_index();k++)
//			{
//				rssi_cell_rankList[j].learned_cell_index[k] = Integer.parseInt(seperated[1+i+index+k]);
//			}
			
			i+=(1+index/*+k*/);//i is the offset here
		}
		//Map File Loaded
		if (seperated[i].compareTo("Map_File_Attached:")!=0 )
		{
			return false;
		}

		if (seperated[i+1].contains("/"))
		{
			gMapView.set_new_background_map_filepath(seperated[i+1]);
		}

		gDefines.curr_matrix_x = -1;
		gDefines.curr_matrix_y = -1;
		
		return true;
	}
}
