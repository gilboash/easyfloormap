/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:       LoadMapImageActivity.java

 * DESCRIPTION:     Loading a map image from the list activity (under zz_maps folder)
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





import java.io.File;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class LoadMapImageActivity extends Activity {
	ListView   listView;
	ArrayAdapter<String> arrayAdapter;
	public String[] files;
	public final int STATIC_INTEGER_LOAD_MAP_VALUE = 12;
	public final String PUBLIC_STATIC_STRING_LOAD_IMAGE_IDENTIFIER = "load_image_string_indentifier";	
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.loadmapimage);
        
        File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/zz_maps/");
        files = dir.list();
        
        listView = (ListView)findViewById(R.id.listmapimage);
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files);//getResources().getStringArray(R.array.Animals));
		listView.setAdapter(arrayAdapter);
		listView.setClickable(true);
		
		//registerForContextMenu(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent resultIntent = new Intent();
				resultIntent.putExtra(PUBLIC_STATIC_STRING_LOAD_IMAGE_IDENTIFIER, files[arg2]);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
				
			}
		   });
        
    }
    

    
    
}
