/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:       ImportPackageThread.java

 * DESCRIPTION:     Import zip package from file manager - I've tested through ASTRO only
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
