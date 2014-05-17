/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       ImportPackageThread.java

  DESCRIPTION:    Import zip package from file manager - I've tested through ASTRO only

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/



package iScans.wifiscans;
import android.net.wifi.*;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.widget.Toast;

import java.util.List;




public class ImportPackageThread extends Thread {

	private static final String TAG = "IndoorPlaceMe:ImportZipPAckageThread";
	private MainActivity main;
	private Defines publicDefines;
	String file_path;
	public ImportPackageThread(MainActivity hello,Defines defineVars)
	{
		publicDefines = defineVars;
		main = hello;
		file_path = publicDefines.file_path_to_import;
	}
	
	
	public void run()
	{

		if (!file_path.contains("zip"))
		{
			Log.d(TAG,"File path returned "+file_path+" not a zip!");
			//Toast.makeText(main, "File path returned "+file_path+" not a zip!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		try
	  	  {        			
    		unzipFiles unzi = new unzipFiles(file_path, Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/");
    		unzi.unzip();
    		Log.d(TAG,"Success importing, now Load this map");
    		//Toast.makeText(main, "Success importing, now Load this map", Toast.LENGTH_LONG).show();
	  	  }
	  	  catch(Exception e)
	  	  {
	  		Log.d(TAG,"Import map file returned a problem");
	  		//Toast.makeText(main, "Import map file returned a problem", Toast.LENGTH_SHORT).show();
	  	  }
		
	  	//main.external_post_invalidate();		
	  	return;
	}
}
