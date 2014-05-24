/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       ChangeMatrixName.java

  DESCRIPTION:    activity page called before mapping new floor to 
  				  determine the matrix resolution

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

public class ChangeMatrixName extends Activity {
	private static final String TAG = "ChangeMatrixName";
	public final String PUBLIC_STATIC_STRING_MATRIX_SIZE = "matrix_size";
	
	private RadioButton rButton,rButton2,rButton3;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrixsize);

        Log.d(TAG,"ChangeMatrixName on-created ");
        Button next = (Button) findViewById(R.id.button1);
        rButton = (RadioButton) findViewById(R.id.radio0);
        rButton2 = (RadioButton) findViewById(R.id.radio1);
        rButton3 = (RadioButton) findViewById(R.id.radio2);
        
        
        

        
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	switch (view.getId()) {
		    		case R.id.button1:
		    			String speed_value="";
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
		                
		                
		                
		                
	    			   Intent intent = new Intent();
	    			   intent.putExtra(PUBLIC_STATIC_STRING_MATRIX_SIZE, speed_value);
	    			   
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
