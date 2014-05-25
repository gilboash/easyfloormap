/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:        ScanCycleThread.java

 * DESCRIPTION:    Invoking android continues background scan activity 
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
import android.widget.Toast;
import android.content.Context;
import android.widget.Toast;

import java.util.List;


public class ScanCycleThread extends Thread {

	private MainActivity main;
	private Defines publicDefines;
	private WifiManager wifiMngr;
	//private LocatorView locView;
	private MapView mapv;
	public ScanCycleThread(MainActivity hello,Defines defineVars,WifiManager wifi, MapView mv)
	{
		mapv=mv;
		wifiMngr = wifi;
		publicDefines = defineVars;
		main = hello;
		//locView = locv;
	}
	
	
	public void run()
	{
		while (true)
		{		
            try
            {
            	ScanCycleThread.sleep(publicDefines.SPEED);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            //locView.postInvalidate();
//            mapv.postInvalidate();
            wifiMngr.startScan();
		}

	}
}
