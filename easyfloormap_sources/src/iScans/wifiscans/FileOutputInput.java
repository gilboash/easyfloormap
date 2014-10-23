/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:       FileOutputInput.java

 * DESCRIPTION:     File Input output handlers for loading maps 
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

import java.io.*;

import android.os.Environment;
import android.util.Log;
import android.widget.*;

public class FileOutputInput {

	private static final String TAG = "IndoorPlaceMe:FileOutputInput";
	public String gFileLoc;
	private MainActivity mActivity;
	public FileOutputInput(MainActivity main_activity)
	{
		gFileLoc = Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/wifilocator.txt";
		mActivity = main_activity;
	}
	
	public void set_file_location(String loc)
	{
		gFileLoc = loc;
	}
	
		
	public void write_to_file(String body)
	{
		try
		{
			File myFile = new File(gFileLoc);
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(body);
			myOutWriter.close();
			fOut.close();
			
			Log.d(TAG,"Done writing SD "+ gFileLoc);
			Toast.makeText(mActivity,
					"Done writing SD "+ gFileLoc,
					Toast.LENGTH_SHORT).show();

		}
		catch (Exception e) {
			Log.d(TAG,"Exception "+e.getMessage());
			Toast.makeText(mActivity, e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}
	
	public String read_from_file()
	{
		String aBuffer = "";
		try {
			File myFile = new File(gFileLoc);
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
					new InputStreamReader(fIn));
			String aDataRow = "";
			
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			//txtData.setText(aBuffer);
			myReader.close();

			Toast.makeText(mActivity,
					gFileLoc+" Loaded",
					Toast.LENGTH_SHORT).show();
			
			Log.d(TAG,gFileLoc+" Loaded");
		} catch (Exception e) {
			Toast.makeText(mActivity, e.getMessage(),
					Toast.LENGTH_SHORT).show();
			
			Log.d(TAG,"File Loading failed "+e.getMessage());
		}
		
		return aBuffer;
	}
	
	
	
	
}
