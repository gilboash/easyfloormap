/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:        SaveMapFileActivity.java

 * DESCRIPTION:    Save Map File and map image activity
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

import android.widget.Button;

import android.content.IntentFilter;
import android.widget.EditText;

import android.view.View.OnClickListener;



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

public class SaveMapFileActivity extends Activity implements OnClickListener {
	ListView   listView;
	ArrayAdapter<String> arrayAdapter;
	public String[] files;
	public final int STATIC_INTEGER_SAVE_VALUE = 11;
	public final String PUBLIC_STATIC_STRING_SAVE_FILE_IDENTIFIER = "save_file_string_indentifier";
	private Button savebutton;
	private EditText savefilepath;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.savefile);
        
        File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/wifilocatormaps/");
        files = dir.list();
        for (int j=0;j<files.length; j++)
        {
        	if ( files[j].contains("zz_maps") || files[j].contains("zz_share_f"))
        	{
        		files[j]="";
        	}
        }
        //files[1].getBytes()
        //files[files.length-1]="";
        //files[files.length-2]="";
        savebutton = (Button)findViewById(R.id.button10);
        savebutton.setOnClickListener(this);
        savefilepath = (EditText)findViewById(R.id.savemapfilename);
		
        listView = (ListView)findViewById(R.id.listView);
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files);//getResources().getStringArray(R.array.Animals));
		listView.setAdapter(arrayAdapter);
		listView.setClickable(true);
		
		//registerForContextMenu(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub				
				savefilepath.append(files[arg2]);
			}
		   });
        
    }
    
    @Override
    public void onClick(View v) {
    	String path = savefilepath.getText().toString();
		Intent resultIntent = new Intent();
		resultIntent.putExtra(PUBLIC_STATIC_STRING_SAVE_FILE_IDENTIFIER, path);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();    	
    
    }
    
    
}
