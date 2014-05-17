/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       ScanCycleThread.java

  DESCRIPTION:    Invoking android continues background scan activity 
  
  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/



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
