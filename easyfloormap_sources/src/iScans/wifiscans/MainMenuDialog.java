/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:        MainMenuDialog.java

 * DESCRIPTION:     Main Menu dialog options handler
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


import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;



public class MainMenuDialog extends Activity implements View.OnClickListener {

	
	public final String PUBLIC_STATIC_MAIN_MENU_CHOICE = "menu_choice";
	

    private EditText mEditText;
    private TextView	txtView;
    private Button mLoadold,mMapFloor,mImport,mFindMe,mImportWeb,mInstructions,mStopFindMe;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenudialog);
        
        txtView = (TextView) findViewById(R.id.textView1);
        //txtView.append("For more info and instructions, please visit my website");
        
        mLoadold = (Button) findViewById(R.id.Button03);
        mLoadold.setOnClickListener(this);
        
        mMapFloor = (Button) findViewById(R.id.Button04);
        mMapFloor.setOnClickListener(this);
        mImport = (Button) findViewById(R.id.Button01);
        mImport.setOnClickListener(this);
        mFindMe = (Button) findViewById(R.id.Button02);
        mFindMe.setOnClickListener(this);
        
        mStopFindMe = (Button) findViewById(R.id.Button07);
        mStopFindMe.setOnClickListener(this);
        
         
        
        mImportWeb = (Button) findViewById(R.id.Button05);
        mImportWeb.setOnClickListener(this);
        
        mInstructions = (Button) findViewById(R.id.Button06);
        mInstructions.setOnClickListener(this);

        

    }

    
    
    
    @Override
    public void onClick(View v) {
    	String selection="";
    	switch (v.getId()) {    	   	
	    	case R.id.Button03:
	    		selection="LoadOld";
	    		break;
	    	case R.id.Button04:
	    		selection="MapFloor";
	    		break;
	    	case R.id.Button01:
	    		selection="Import";
	    		break;
	    	case R.id.Button02:
	    		selection="LocateRemote"; 
	    		break;
	    	case R.id.Button05:
	    		selection="VisitGitDB";
	    		break;
	    	case R.id.Button06:
	    		selection="InstructionWB";
	    		break;
	    	case R.id.Button07:
	    		selection="StopLocatingRemote";
	    		break;
	    	default:
	    		selection="none";
	    		
      		
    	}
    	
		   Intent intent = new Intent();
		   intent.putExtra(PUBLIC_STATIC_MAIN_MENU_CHOICE, selection);
           setResult(RESULT_OK, intent);
           finish();

    	
    }
}