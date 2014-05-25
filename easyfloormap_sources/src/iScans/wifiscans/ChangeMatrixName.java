/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:       ChangeMatrixName.java

 * DESCRIPTION:    activity page called before mapping new floor to 
  				  determine the matrix resolution
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
