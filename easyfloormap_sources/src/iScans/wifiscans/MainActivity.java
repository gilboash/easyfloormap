/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       MainActivity.java

  DESCRIPTION:    Main application engine.

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki


***********************************************************************************************************************/



package iScans.wifiscans;


import iScans.wifiscans.R.string;

import java.io.File;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import iScans.wifiscans.tcpserverlisten;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;



//import com.ahs.androheb.drawlines.R;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.*;
import android.content.Context;
import android.widget.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.IntentFilter;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import iScans.wifiscans.MainMenuDialog;

//FragmentDialogDemo extends FragmentActivity implements EditNameDialogListener {

public class MainActivity extends Activity implements View.OnClickListener {
	public WifiManager wifiManager;
	//private EditText myEditText;

	private tcpserverlisten _tcpServer;
	private ServerSocket ss;
	private Socket		 socket;
	
	
	
	//private Button toggleWifi;
    Button mButtonLoadSaveMap;
    Button mButtonLocateMeNow;
    Button mButtonLearningMode;
    Button mButtonOptionViewSwitch;

	public Defines defineVariables;
	private ScanCycleThread scanCycThreadRun;
	private ImportPackageThread importZipPackageThread;
	BroadcastReceiver receiver;
	
	BroadcastScan	broadcast_utils;
	private Intent ChannelChangeListenerIntent = null;
	
	private static final String TAG = "IndoorPlaceMe:MainActivity";
	
	private MapView myMapView;
	//private LocatorView locationView;
	private ssid_rssi_prms ssidRssiParams;
	private ActivityInfo d;
	
	private FileOutputInput fileStream;
	
	private String new_file_name;
	private double[] gps_location;
	
	private zipFiles compressMethod;
	
	public final int STATIC_INTEGER_VALUE = 10;
	public final String PUBLIC_STATIC_STRING_LOAD_FILE_IDENTIFIER = "load_file_string_indentifier";

	public final int STATIC_INTEGER_SAVE_VALUE = 11;
	public final String PUBLIC_STATIC_STRING_SAVE_FILE_IDENTIFIER = "save_file_string_indentifier";
	
	public final int STATIC_INTEGER_LOAD_MAP_VALUE = 12;
	public final String PUBLIC_STATIC_STRING_LOAD_IMAGE_IDENTIFIER = "load_image_string_indentifier";	

	public final int STATIC_INTEGER_LOAD_IMAGE_GALLERY_VALUE = 13;
	public final String PUBLIC_STATIC_STRING_LOAD_IMAGE_GALLERY_IDENTIFIER = "load_image_gallery_indentifier";
	
	public final int STATIC_INTEGER_IMPORT_EXTERNAL_MAP = 14;
	//public final String PUBLIC_STATIC_STRING_SAVE_FILE_IDENTIFIER = "save_file_string_indentifier";
	public final int STATIC_SELECTION = 15;
	private final String PUBLIC_SELECTION_IDENTIFIER = "main_menu_selection";
	
	public final int STATIC_SPEED_SCAN = 16;
	public final String PUBLIC_STATIC_STRING_SPEED = "speed_scan";
	public final String PUBLIC_STATIC_STRING_CONSTRAIN_SEARCH_SPACE = "space_size";
	
	
	public final int STATIC_MAIN_MENU_CHOICE = 17;
	public final String PUBLIC_STATIC_MAIN_MENU_CHOICE = "menu_choice";
	
	
	public final int STATIC_MATRIX_SIZE = 18;
	public final String PUBLIC_STATIC_STRING_MATRIX_SIZE = "matrix_size";;
	
	
	
	private MainActivity mainthis;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mainthis = this;
        super.onCreate(savedInstanceState);
        
        //Lock screen rotation
        this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Create folders if not exist!
        File folder = new File(Environment.getExternalStorageDirectory () + "/wifilocatormaps");
        boolean success = false;
        if(!folder.exists()){
            success = folder.mkdir();
        }
        folder = new File(Environment.getExternalStorageDirectory () + "/wifilocatormaps/zz_maps");
        success = false;
        if(!folder.exists()){
            success = folder.mkdir();
        }
        
        folder = new File(Environment.getExternalStorageDirectory () + "/wifilocatormaps/zz_share_format");
        success = false;
        if(!folder.exists()){
            success = folder.mkdir();
        }
        
        //defines
        defineVariables	 = new Defines(this.getWindowManager().getDefaultDisplay(),this.getWindowManager().getDefaultDisplay().getHeight(),this);
        
        //locationView = new LocatorView(this, defineVariables);
        ssidRssiParams = new ssid_rssi_prms(defineVariables);
        myMapView = new MapView(this, defineVariables,this,ssidRssiParams);
        
        //ssidRssiParams.save_map_image_file_path(Environment.getExternalStorageDirectory().getPath() + "/wifilocatormaps/maps/"+defineVariables.DEFAULT_MAP);
        
        myMapView.setViews();
        //setContentView(myMapView);
        setContentView(R.layout.activity_main);
        ((LinearLayout) findViewById(R.id.root2)).addView(myMapView);

        mButtonLoadSaveMap = (Button)findViewById(R.id.button1);
        mButtonLoadSaveMap.setOnClickListener(this);
        mButtonLocateMeNow = (Button)findViewById(R.id.button2);
        mButtonLocateMeNow.setOnClickListener(this);
        mButtonLearningMode = (Button)findViewById(R.id.toggleButton1);
        mButtonLearningMode.setOnClickListener(this);
        
        mButtonOptionViewSwitch = (Button)findViewById(R.id.button3);
        mButtonOptionViewSwitch.setOnClickListener(this);
        
        
        fileStream =  new FileOutputInput(this);
        
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        
        wifiManager.setWifiEnabled(true);
        
        //myEditText = (EditText)findViewById(R.id.editText1);
		
		// Register Broadcast Receiver
		if (receiver == null)
			receiver = new BroadcastScan(this,defineVariables,ssidRssiParams);

		
		
		ChannelChangeListenerIntent = registerReceiver(receiver, new IntentFilter(
					WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		
		broadcast_utils = new BroadcastScan(this, defineVariables, ssidRssiParams);
		
		scanCycThreadRun = new ScanCycleThread(this, defineVariables,wifiManager,myMapView);
		scanCycThreadRun.start();
		
		
		
		learning_mode_off_make_buttons_unvisible();
		
		myMapView.set_new_marker_bitmap(1);
		Log.d(TAG,"Wifi scans launched, all threads loaded and folder maked under /sdcard/wifilocatormaps/");
		
//		Intent myIntent = new Intent(this, MainMenuDialogActivity.class);
//        startActivityForResult(myIntent, STATIC_SELECTION);		
//		
		myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
		Intent myIntent = new Intent(this, MainMenuDialog.class);
        startActivityForResult(myIntent, STATIC_MAIN_MENU_CHOICE);
    }

    
	@Override
	public void onPause() {
		super.onPause();
		if (receiver!=null)
		{
			Log.d(TAG,"Activity paused");
			unregisterReceiver(receiver);
			receiver=null;
		}
		

	    
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (receiver==null)
		{
			Log.d(TAG,"Activity resumed, resuming broadcast scans");
			receiver = new BroadcastScan(this,defineVariables,ssidRssiParams);
			ChannelChangeListenerIntent = registerReceiver(receiver, new IntentFilter(
						WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		}
	}
		
    public void set_gps_loc()
    {
		try
		{
			gps_location = getGPS();
			Log.d(TAG,"Setting gps location, latutide "+gps_location[0]+" long_latitude "+gps_location[1]);
			//Toast.makeText(this, "GPS Location is Latitude: " + gps[0] + "Longitude: " + gps[1], Toast.LENGTH_SHORT).show();				
		}
		catch (Exception e)
		{
			gps_location[0]=0;
			gps_location[1]=0;
			Toast.makeText(this, "Failed to get gps loc", Toast.LENGTH_SHORT).show();
		}

    }
    private void LoadMapMethod()
    {
		Log.d(TAG,"Locating mode - Load map requested");
		Intent myIntent = new Intent(this, LoadMapFileActivity.class);
        startActivityForResult(myIntent, STATIC_INTEGER_VALUE);
        external_post_invalidate();
    }
    
    private void ImportExternalMapPackageMethod()
    {
		Log.d(TAG,"Import external map package requested");
		Intent myIntent = new Intent(Intent.ACTION_GET_CONTENT);
		myIntent.setType("*/*");
		startActivityForResult(myIntent,STATIC_INTEGER_IMPORT_EXTERNAL_MAP);
		external_post_invalidate();
    }
    
    private void learning_mode_toggle_method()
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (defineVariables.gLearningMode)
		{
			Log.d(TAG,"Learning mode off - last chance to save the map");
			builder.setMessage("Do you want to save this map?").setPositiveButton("Yes", dialogToggleLearningClickListener)
		    	.setNegativeButton("No", dialogToggleLearningClickListener).show();	
		}
		else
		{
			Log.d(TAG,"Start Learning new map - Are u sure?");
			builder.setMessage("Learning mode will clear the current mapping").setPositiveButton("New Mapping", dialogToggleLearningClickListener)
	    		.setNegativeButton("Cancel", dialogToggleLearningClickListener).show();
		}
    }
    
    
    
    
    

	private Handler locationEventHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg)
	    {
	    	
	    	String messageRecieved=msg.obj.toString();
	    	if (messageRecieved.contains("SCAN_REPORT:"))
	    	{
	    		__trackRemoteDeviceMsgReceived(messageRecieved);
	    	}
	    	
	    }
	};
 
	
	private void __trackRemoteDeviceMsgReceived(String msg)
	{
		broadcast_utils.trackRemoteDeviceMsgReceived(msg);
	}
	
	


	private void TrackDeviceSocketOpen()
	{
		_tcpServer = new tcpserverlisten(this,ss,socket);
		_tcpServer.start();
		_tcpServer.registerHandler(locationEventHandler);
	}
    
    @SuppressWarnings("deprecation")
	@Override
    public void onClick(View v) {
    	switch (v.getId()) {    	
		    	case R.id.button1:
				{//mButtonLoadSaveMap - Load/Save map depending on the learning mode
					if (defineVariables.gLearningMode==true)
	        		{//SAVE
						if (defineVariables.gTrackRemoteDevice)
						{
							Log.d(TAG,"Learning mode - Reset TCP Port");
							
		    				_tcpServer.tcp_close();
		    				
		    				Toast.makeText(this, "Toggle TCP server remote tracking",Toast.LENGTH_LONG).show();
		    				try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		    				TrackDeviceSocketOpen();
		    				
						}
						else
						{
	//	        			ssidRssiParams.save_current_rssi_mapping_to_file(fileStream);
							Log.d(TAG,"Learning mode - SAVE map requested");
							//GPS location learning?
							
							set_gps_loc();
							Intent myIntent = new Intent(v.getContext(), SaveMapFileActivity.class);
		                startActivityForResult(myIntent, STATIC_INTEGER_SAVE_VALUE);
						}
						
	        			
	        		} else
	        		{//LOAD
	        			//LoadMapMethod();
	        			
	        			//tracking
//	        			Toast.makeText(this, "Open TCP server remote tracking",Toast.LENGTH_LONG).show();
//	        			
//	        			defineVariables.gTrackRemoteDevice = true;
//	        			
//	        			TrackDeviceSocketOpen();
	        			
	        			
	        			
	        			//write the listener that locates the device on the map
	        			
	        		}
		            break;
				}
		    	case R.id.button2:
	    		{//mButtonLocateMeNow - Load Map image OR locate me now
	    			if (defineVariables.gLearningMode)
	    			{//Load Maps
//		    			Intent myIntent = new Intent(v.getContext(), LoadMapImageActivity.class);
//		                startActivityForResult(myIntent, STATIC_INTEGER_LOAD_MAP_VALUE);
	    				Log.d(TAG,"Import map from gallery launched");
	    				Intent myIntent = new Intent(Intent.ACTION_GET_CONTENT);
	    				myIntent.setType("image/*");
	    				startActivityForResult(myIntent,STATIC_INTEGER_LOAD_IMAGE_GALLERY_VALUE);
	    			}
	    			else
	    			{//Import
	    				
	    				myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
	    				Intent myIntent = new Intent(this, MainMenuDialog.class);
	    		        startActivityForResult(myIntent, STATIC_MAIN_MENU_CHOICE);
	    				//ImportExternalMapPackageMethod();
	    			}
	    			
	    			external_post_invalidate();
	                break;
	    		}
		    	case R.id.toggleButton1:
	    		{//mButtonLearningMode - Learning mode on-off
	    			
	    			
	    			learning_mode_toggle_method();
	    			//toggle_learning_mode();
	    			//external_post_invalidate();
	                break;
	    		}
		    		
		    	case R.id.button3:
		    	{

		    		//DROPBOX API trials
//		    		Intent myIntent = new Intent(v.getContext(), DropBoxShareActivity.class);//DBRoulette.class);
//		    		myIntent.putExtra("file_name_to_share","ken");
//	                startActivityForResult(myIntent, STATIC_INTEGER_SHARE_DB_VALUE);
	           		
		    		Log.d(TAG,"Additional menu button3 - Location interval & search radius");
	    			Intent myIntent = new Intent(v.getContext(), Activity2.class);
	    			
	    			myIntent.putExtra("OLD_SPEED", Integer.toString(defineVariables.SPEED) );
	    			myIntent.putExtra("OLD_RADIUS",Integer.toString(defineVariables.CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS));
	    			
	                startActivityForResult(myIntent, STATIC_SPEED_SCAN);
	                
	                
		    		break;
		    	}
		    		
		    	}
    }
    
    public void writeToEditText(String text, Boolean append)
    {

    }


    private void learning_mode_off_make_buttons_unvisible()
    {
    	mButtonLearningMode.setVisibility(View.GONE);
    	
		mButtonLoadSaveMap.setVisibility(View.GONE);
    	//mButtonLoadSaveMap.setText("Track Remote Device");
		
		mButtonLocateMeNow.setText("MainMenu");//string.label_import_map);
    }
    private void toggle_learning_mode(Boolean g_learn_mode)
    {
		if (g_learn_mode)//defineVariables.gLearningMode)
		{
			
			defineVariables.gLearningMode = false;
//			mButtonLearningMode.setVisibility(View.GONE);// setTextColor(Color.BLUE);
//			//mButtonLearningMode.setText(string.label_learning_mode_off);
//			mButtonLoadSaveMap.setVisibility(View.GONE);//setText(string.label_load_image);
//			mButtonLocateMeNow.setText("MainMenu");//string.label_import_map);
			
			
			//Zero matrix location
			defineVariables.curr_matrix_x = -1;
			defineVariables.curr_matrix_y = -1;
			learning_mode_off_make_buttons_unvisible();
		} else
		{
			defineVariables.gLearningMode = true;
			mButtonLearningMode.setVisibility(View.VISIBLE);
			mButtonLoadSaveMap.setVisibility(View.VISIBLE);
			
			mButtonLearningMode.setTextColor(Color.RED);
			mButtonLearningMode.setText(string.label_learning_mode);
			if (defineVariables.gTrackRemoteDevice)
				mButtonLoadSaveMap.setText("Reset TCP Socket");
			else
				mButtonLoadSaveMap.setText(string.label_save_map);
			mButtonLocateMeNow.setText(string.label_import_image);
			defineVariables.curr_matrix_x = 0;
			defineVariables.curr_matrix_y = 0;
			ssidRssiParams.init_ssid_rssi_prms();
		}

		myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
		Log.d(TAG,"Toggle learning mode switch to" +defineVariables.gLearningMode);
		external_post_invalidate();

    }
    
    public boolean onTouchEvent(MotionEvent event)
    {
    	
        float destinationX,destinationY;
        //get the x coordinate of users' press
        destinationX = event.getX();
        //From some reason i need an offset for the touching of the screen- i really dont feel like looking for the reason
        
        destinationY = event.getY()-defineVariables.SCREEN_BORDER*3-defineVariables.SCREEN_BORDER/2;

        switch (event.getAction())
        {
	        case MotionEvent.ACTION_DOWN:	        	
	    		if (defineVariables.gLearningMode)
	        	{//Set new touched location (in learning mode)
	    			Log.d(TAG,"Touch event X=" + destinationX + " Y="+destinationY);
	        		defineVariables.setNewLoc(destinationX, destinationY);
	        		myMapView.postInvalidate();  		
	        	}
	    		else
	    		{//Zoom in or zoom out

	    			switch (defineVariables.ZoomMode)
	    			{
		    			case 0://ZOOMMODE_DFLT
			    				int zoomMode=defineVariables.zoomInWhere(destinationX,destinationY);
				    			myMapView.prepare_zoom_rectangles(zoomMode);
		    				break;
		    			case 1://ZOOMMODE_UPRIGHT
		    				myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
		    				break;
		    			case 2://ZOOMMODE_UPLEFT
		    				myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
		    				break;
		    			case 3://ZOOMMODE_LOWRIGHT
		    				myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
		    				break;
		    			case 4://ZOOMMODE_LOWLEFT
		    				myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
		    				break;
	    			}
	    			
	    			myMapView.postInvalidate();  	
	    			

	    			
	    		}

	        	break;
	        case MotionEvent.ACTION_UP:
	        	break;
	        case MotionEvent.ACTION_MOVE:
	        	break;
        }
        
        return true;
    }
    
    public void external_post_invalidate()
    {
		myMapView.postInvalidate();
    }

    
    @Override 
    public void onActivityResult(int requestCode, int resultCode, Intent data) {     
      super.onActivityResult(requestCode, resultCode, data); 
      switch(requestCode) {
      
      
	      case (STATIC_MATRIX_SIZE) : {
	    	  
	    	  if (resultCode == Activity.RESULT_OK) {
	    		
	    		  
	    		  String selection = data.getStringExtra(PUBLIC_STATIC_STRING_MATRIX_SIZE);
	    		  
	   		      Toast.makeText(this, "Changed matrix size to "+selection, Toast.LENGTH_SHORT).show();
	   		      
	   		      if (selection.contains("1"))
	   		      {
	   		    	  
	   		    	  defineVariables.init_matrix_values(defineVariables.X_CELL_AMOUNT_DFLT,defineVariables.Y_CELL_AMOUNT_DFLT);
	   		      } else if (selection.contains("2"))
	   		      {
	   		    	  defineVariables.init_matrix_values(defineVariables.X_CELL_AMOUNT_MED_DFLT,defineVariables.Y_CELL_AMOUNT_MED_DFLT);
	   		    	  
	   		      } else if (selection.contains("3"))
	   		      {
	   		    	  defineVariables.init_matrix_values(defineVariables.X_CELL_AMOUNT_LOW_DFLT,defineVariables.Y_CELL_AMOUNT_LOW_DFLT);
	   		      }
	   		      else
	   		      {
	   		    	Toast.makeText(this, "Error!! no such size", Toast.LENGTH_SHORT).show();
	   		      }
	   		      
	   		      
	    		  
	  			myMapView.set_new_marker_bitmap(1);
	  			//external_post_invalidate();
	  			toggle_learning_mode(false);
	    	  }
	    	  	
	    	  
				
				
	    	  
	    	  break;
	      }
	      case (STATIC_SPEED_SCAN) : {
	    	  if (resultCode == Activity.RESULT_OK) {
	    		  String selection = data.getStringExtra(PUBLIC_STATIC_STRING_SPEED);
	   		      Toast.makeText(this, "Changed refresh interval to "+selection, Toast.LENGTH_SHORT).show();
	   		      int speed_value=defineVariables.REFRESH_SPEED_MED_FREQ;
	   		      if (selection.contains("1"))
	   		      {
	   		    	  speed_value = defineVariables.REFRESH_SPEED_HIGH_FREQ;
	   		      } else if (selection.contains("2"))
	   		      {
	   		    	  speed_value = defineVariables.REFRESH_SPEED_MED_FREQ;
	   		      } if (selection.contains("3"))
	   		      {
	   		    	  speed_value = defineVariables.REFRESH_SPEED_LOW_FREQ;
	   		      }
	   		      defineVariables.setSpeed(speed_value);
	   		      
	   		      
	   		      String space_size = data.getStringExtra(PUBLIC_STATIC_STRING_CONSTRAIN_SEARCH_SPACE);
	   		      if (space_size.contains("2"))
	   		      {
	   		    	  defineVariables.CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS = 2;
	   		      }
	   		      else if (space_size.contains("5"))
	   		      {
	   		    	  defineVariables.CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS = 5;
	   		      }
	   		      else if (space_size.contains("0"))
	   		      {
	   		    	  defineVariables.CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS = 100;
	   		      }
	   		      
	   		   
	    	  }
	    	  break;
	      }

	      case (STATIC_MAIN_MENU_CHOICE) : {
	    	  if (resultCode == Activity.RESULT_OK) {
	    		   String selection = data.getStringExtra(PUBLIC_STATIC_MAIN_MENU_CHOICE);
	    		   if (selection.contains("LoadOld"))
	    		   {
	    			   LoadMapMethod();  
	    		   }
	    		   else if (selection.contains("MapFloor"))
	    		   {
	    			   
	    			   Intent myIntent = new Intent(mainthis, ChangeMatrixName.class);
		                startActivityForResult(myIntent, STATIC_MATRIX_SIZE);
	    			   
//	    			   toggle_learning_mode(false);
	    		   } 
	    		   else if (selection.contains("Import"))
	    		   {
	    			   ImportExternalMapPackageMethod();
	    		   }
	    		   else if (selection.contains("LocateRemote"))
	    		   {
	    			   	Toast.makeText(this, "Open TCP server remote tracking",Toast.LENGTH_LONG).show();
	    			   
	        			defineVariables.gTrackRemoteDevice = true;
	        			
	        			TrackDeviceSocketOpen();
		    		   
	    		   }
	    		   else if (selection.contains("StopLocatingRemote"))
	    		   {

	    				if (defineVariables.gTrackRemoteDevice)
	    				{
		    				defineVariables.gTrackRemoteDevice = false;
		    				_tcpServer.tcp_close();
		    				
		    				Toast.makeText(this, "Close TCP server remote tracking",Toast.LENGTH_LONG).show();
	    				}
	    		   }
	    		   else if (selection.contains("VisitGitDB"))
	    		   {
	    			   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://easyfloormap.weebly.com/download-map-packages.html"));
	    			   startActivity(browserIntent);
	    		   }
	    		   else if (selection.contains("InstructionWB"))
	    		   {
	    			   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://easyfloormap.weebly.com/instructions.html"));
	    			   startActivity(browserIntent);
	    		   }
	    		   //Toast.makeText(this, "Selected "+selection, Toast.LENGTH_SHORT).show();
	    	  }
	    	  break;
	      }
        case (STATIC_INTEGER_VALUE) : { //LOAD MAP FILE method return
          if (resultCode == Activity.RESULT_OK) { 
        	  
	          String newLoc = data.getStringExtra(PUBLIC_STATIC_STRING_LOAD_FILE_IDENTIFIER);
	          fileStream.set_file_location(Environment.getExternalStorageDirectory().getPath() + "/wifilocatormaps/"+newLoc);
	          Toast.makeText(this, "Loading file path "+newLoc, Toast.LENGTH_SHORT).show();
	          
	          Log.d(TAG,"Loading map return location "+newLoc);
	          
	          if (!ssidRssiParams.load_rssi_mapping_from_file(fileStream))
	  			{
	        	  	Log.d(TAG,"Loading map Failed");
	  				Toast.makeText(this, "Failed to load map", Toast.LENGTH_SHORT).show();
	  			}
	  			else
	  			{
	  				Log.d(TAG,"Loading map Success");
	  				Toast.makeText(this, "Map Loaded Succesfully", Toast.LENGTH_SHORT).show();
	  				
	  				myMapView.set_new_marker_bitmap(1);
	  				//myMapView.set_new_background_map_filepath(ssidRssiParams.gMapFileLoc);
	  			}
	          myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
	          external_post_invalidate();
	          // TODO Update your TextView.
          } 
          break; 
        }
        case (STATIC_INTEGER_SAVE_VALUE) : {//SAVE MAP FILE method return
        	if (resultCode == Activity.RESULT_OK) {
        	  String newLoc = data.getStringExtra(PUBLIC_STATIC_STRING_SAVE_FILE_IDENTIFIER);
  	          fileStream.set_file_location(Environment.getExternalStorageDirectory().getPath() + "/wifilocatormaps/"+newLoc);
  	          Toast.makeText(this, "Saving file path "+newLoc, Toast.LENGTH_SHORT).show();
  	          ssidRssiParams.save_current_rssi_mapping_to_file(fileStream,newLoc,gps_location[0],gps_location[1]);
  	          
  	        Log.d(TAG,"Mapping is saved to location "+newLoc);
  	          toggle_learning_mode(true);
  	          try
  	          {
  	        	new_file_name = newLoc;
  	        	Log.d(TAG,"Sharing option user select");
	  	          Thread.sleep(2000);
		    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    		builder.setMessage("Would you like to share?").setPositiveButton("Yes", dialogClickListener)
		    		    .setNegativeButton("No", dialogClickListener).show();
  	          }
  	          catch (Exception e)
  	          {
  	        	  Toast.makeText(this, "Failed to share saved map", Toast.LENGTH_SHORT).show();
  	          }
        	}
 			break;
        }
        //NEED TO REMOVE
        case (STATIC_INTEGER_LOAD_MAP_VALUE) : {//LOAD Map Image method return
        	if (resultCode == Activity.RESULT_OK) {
          	  	  String newLoc = data.getStringExtra(PUBLIC_STATIC_STRING_LOAD_IMAGE_IDENTIFIER);
          	  	  String Full_file_path = Environment.getExternalStorageDirectory().getPath() + "/wifilocatormaps/zz_maps/"+newLoc;
    	          Toast.makeText(this, "Image "+newLoc+" Loaded", Toast.LENGTH_SHORT).show();
    	          Log.d(TAG,"Image "+newLoc+" Loaded");
    	          myMapView.set_new_background_map_filepath(Full_file_path);
    	          external_post_invalidate();
          	}
			break;
        }
        case (STATIC_INTEGER_LOAD_IMAGE_GALLERY_VALUE): {//Load image from gallery method return
        	if (resultCode == Activity.RESULT_OK) {
        	  	  try
        	  	  {
        	  		InputStream stream = getContentResolver().openInputStream(data.getData());
        	  		myMapView.set_new_background_map_stream(stream);
        			myMapView.prepare_zoom_rectangles(defineVariables.ZOOMMODE_DFLT);
        	  		Log.d(TAG,"Loading image from gallery stream "+data.getData().toString());
                    stream.close();	  
        	  	  }
        	  	  catch(Exception e)
        	  	  {
        	  		Log.d(TAG,"Image path returned a problem");
        	  		Toast.makeText(this, "Image path returned a problem", Toast.LENGTH_SHORT).show();
        	  	  }
        		
        	  	  external_post_invalidate();
        	}
        	break;
        	
        }
        
        case (STATIC_INTEGER_IMPORT_EXTERNAL_MAP): {
        	if (resultCode == Activity.RESULT_OK) {
        		defineVariables.file_path_to_import =     	data.getData().getPath();
        		
        		importZipPackageThread = new ImportPackageThread(this,defineVariables);
        		importZipPackageThread.start();
        		
        		Toast.makeText(this, "Importing ZIP package, please hold on few seconds, it might take a while", Toast.LENGTH_LONG).show();
        		Toast.makeText(this, "Not yet my friend, please wait.........", Toast.LENGTH_LONG).show();
        		Toast.makeText(this, "Whats the rush? please wait.........", Toast.LENGTH_LONG).show();
        		Toast.makeText(this, "A little bit longer, please wait.........", Toast.LENGTH_LONG).show();
        		Toast.makeText(this, "Just wait!!", Toast.LENGTH_LONG).show();
        		Toast.makeText(this, "Not yet....", Toast.LENGTH_LONG).show();
        		Toast.makeText(this, "And now go to Load existing Map and look for the imported map", Toast.LENGTH_LONG).show();
//        		String file_path= data.getData().getPath();
//        		if (!file_path.contains("zip"))
//        		{
//        			Log.d(TAG,"File path returned "+file_path+" not a zip!");
//        			Toast.makeText(this, "File path returned "+file_path+" not a zip!", Toast.LENGTH_SHORT).show();
//        			break;
//        		}
//        		
//        		
//        		try
//      	  	  {        			
//		    		unzipFiles unzi = new unzipFiles(file_path, Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/");
//		    		unzi.unzip();
//		    		Log.d(TAG,"Success importing, now Load this map");
//		    		Toast.makeText(this, "Success importing, now Load this map", Toast.LENGTH_LONG).show();
//      	  	  }
//      	  	  catch(Exception e)
//      	  	  {
//      	  		Log.d(TAG,"Import map file returned a problem");
//      	  		Toast.makeText(this, "Import map file returned a problem", Toast.LENGTH_SHORT).show();
//      	  	  }
//      		
//      	  	  external_post_invalidate();
      	}
        	break;
        }
        
      } 
    }
    
    //Send me the last saved file and map
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked	    		
            	send_gmail_to_db_with_attachements(new_file_name);
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
            }
        }
    };

    //learning mode toggle dialog listener
    DialogInterface.OnClickListener dialogToggleLearningClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked
         
            	if (defineVariables.gLearningMode)
            	{
            		//Save map
					set_gps_loc();
					Log.d(TAG,"Last chance save MAP");
					Intent myIntent = new Intent(mainthis, SaveMapFileActivity.class);
	                startActivityForResult(myIntent, STATIC_INTEGER_SAVE_VALUE);	    		
            		
            	}
            	else
            	{
            		toggle_learning_mode(false);
            	}
            	//send_gmail_to_db_with_attachements(new_file_name);
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
            	if (defineVariables.gLearningMode)
            	{
            		toggle_learning_mode(true);
            	}
            	else
            	{
            		
            	}
                break;
            }
        }
    };
    
    
    void send_gmail_to_db_with_attachements(String new_file_name)
    {
    	String a = fileStream.gFileLoc;
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		
		emailIntent.setType("message/rfc822");
		//emailIntent.setType("application/octet-stream");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gilboash@gmail.com"});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Indoor map location map {"+new_file_name+" }");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<b>Please add u'r description here:\n Where did u map this? \nWhen?\nOr just how are u doing ???\n Thanks alot\nGilboa\n</b>"));
		
		Log.d(TAG,"Send Gmail file name "+new_file_name);
		
		//ArrayList<Uri> uris = new ArrayList<Uri>();
		try
		{
			String[] files = new String[2];
			files[0] = Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/"+new_file_name;
			files[1] = Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/zz_maps/"+new_file_name+".jpg";
		    compressMethod = new zipFiles(files, Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/zz_share_format/"+new_file_name+".zip");
			compressMethod.zip();
			
			//Thread.sleep(1000);
			
			File myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/zz_share_format/"+new_file_name+".zip");
			Uri uri = Uri.fromFile(myFile);
			emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
		}
		catch (Exception e)
		{
			Log.d(TAG,"failed to compress and send email");
			Toast.makeText(this, "failed to compress and send email", Toast.LENGTH_SHORT).show();
		}
		//uris.add(uri);
		//myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/maps/"+new_file_name+".jpg");
		//uri = Uri.fromFile(myFile);
		//uris.add(uri);
		
		

		//emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		try
		{
			Log.d(TAG,"Launch mail activity");
			startActivity(Intent.createChooser(emailIntent, "Choose your Email provider (Do not message):"));
    			
		}
		catch (Exception e)
		{
			Log.d(TAG,"There are no email clients installed.");
			Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
    }
    
    private double[] getGPS() {
        LocationManager lm = (LocationManager) getSystemService( Context.LOCATION_SERVICE);  
        List<String> providers = lm.getProviders(true);

        Log.d(TAG,"get GPS from last known location");
        /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
        Location l = null;

        for (int i=providers.size()-1; i>=0; i--) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) break;
        }

        double[] gps = new double[2];
        if (l != null) {
                gps[0] = l.getLatitude();
                gps[1] = l.getLongitude();
        }
        return gps;
   }

}
