/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       LoadMapImageActivity.java

  DESCRIPTION:    Loading a map image from the list activity (under zz_maps folder)

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/


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
