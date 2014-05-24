/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       Activity2.java

  DESCRIPTION:    Location interval & search radius menu page handler

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/

package iScans.wifiscans;

import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class Activity2 extends Activity {
	private static final String TAG = "IndoorPlaceMe:Activity2";
	public final String PUBLIC_STATIC_STRING_SPEED = "speed_scan";
	public final String PUBLIC_STATIC_STRING_CONSTRAIN_SEARCH_SPACE = "space_size";
	
	public final int REFRESH_SPEED_HIGH_FREQ = 5000;
	public final int REFRESH_SPEED_MED_FREQ  = 10000;
	public final int REFRESH_SPEED_LOW_FREQ  = 12000;
	
	

	
	private RadioButton rButton,rButton2,rButton3,rButton_2cells,rButton_5cells,rButton_full;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runneractivity);
        String old_speed="",old_radius="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            old_speed = extras.getString("OLD_SPEED");
            old_radius =  extras.getString("OLD_RADIUS");
        }
        
        
        Log.d(TAG,"Activity2 on-created - not relevant yet "+old_speed + " "+old_radius);
        Button next = (Button) findViewById(R.id.button1);
        rButton = (RadioButton) findViewById(R.id.radio0);
        rButton2 = (RadioButton) findViewById(R.id.radio1);
        rButton3 = (RadioButton) findViewById(R.id.radio2);

        rButton.setChecked(old_speed.contains(Integer.toString(REFRESH_SPEED_HIGH_FREQ)));
        rButton2.setChecked(old_speed.contains(Integer.toString(REFRESH_SPEED_MED_FREQ)));
        rButton3.setChecked(old_speed.contains(Integer.toString(REFRESH_SPEED_LOW_FREQ)));
        
        
        rButton_2cells = (RadioButton) findViewById(R.id.radio3);
        rButton_5cells = (RadioButton) findViewById(R.id.radio4);
        rButton_full 	= (RadioButton) findViewById(R.id.radio5);
        
        rButton_2cells.setChecked(old_radius.contains("2"));
        rButton_5cells.setChecked(old_radius.contains("5"));
        rButton_full.setChecked(old_radius.contains("100"));
        
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	switch (view.getId()) {
		    		case R.id.button1:
		    			String speed_value="",space_value="";
		    			if (rButton.isChecked())
		                {
		               	 speed_value="1";
		   	    		   
		                }
		                if (rButton2.isChecked())
		                {
		                	speed_value="2";
		                }
		                if (rButton3.isChecked())
		                {
		                	speed_value="3";
		                }
		                
		                
		                if (rButton_2cells.isChecked())
		                {
		                	space_value="2";
		   	    		   
		                }
		                if (rButton_5cells.isChecked())
		                {
		                	space_value="5";
		                }
		                if (rButton_full.isChecked())
		                {
		                	space_value="0";
		                }
		                
	    			   Intent intent = new Intent();
	    			   intent.putExtra(PUBLIC_STATIC_STRING_SPEED, speed_value);
	    			   intent.putExtra(PUBLIC_STATIC_STRING_CONSTRAIN_SEARCH_SPACE,space_value);
	                   setResult(RESULT_OK, intent);
	                   finish();
		    		   break;
//		    		case R.id.button4:
//		    			
//		    			break;
//		    		case R.id.button5:
//		    			break;
//		    		case R.id.button6:
//		    			break;
            	}
            	
             
            }

        });
    }
    

}
