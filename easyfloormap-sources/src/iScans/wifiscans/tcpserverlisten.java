/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       tcpserverlisten.java

  DESCRIPTION:    Initial trial for recieving the RSSI's from a distant device
  				  so this is the tcp socket server.
  				  no TLV set for messages - just plain strings so need to be modified once working 

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/



package iScans.wifiscans;
import android.R.bool;
import android.content.Context;
import android.widget.Toast;

import java.util.List;
//import java.;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;


import android.util.Log;





public class tcpserverlisten extends Thread {

	    //private TextView textDisplay;
		private static final int TCP_SERVER_PORT = 21111;
		private ServerSocket local_ss;
		private Socket local_socket;
		private MainActivity _main;
		public Boolean accept_connections;
		private BufferedReader in ;
		private int last_recieved_ack = 0;
		private Handler handler;
	    public tcpserverlisten(MainActivity __main ,ServerSocket ss, Socket s) {
	        local_ss = ss;
	        local_socket = s;
	        _main = __main;
	        accept_connections = false;
	    }
		    
	    @Override
	    public void run() {
	        //super.run();

	    
	       
	    	
		    	try {
		    		
		    		local_ss = new ServerSocket(TCP_SERVER_PORT);
		    		 while (true)
		 	        {
		    			 
			    		
						//ss.setSoTimeout(10000);
						//accept connections
			    		if (!accept_connections)
			    		{
			    			local_socket = local_ss.accept();

			    			
			    			
			    			
			    			in = new BufferedReader(new InputStreamReader(local_socket.getInputStream()));
							BufferedWriter out = new BufferedWriter(new OutputStreamWriter(local_socket.getOutputStream()));
							
							
							
							//Wait for ACK on the connection
							String incomingMsg = in.readLine() + System.getProperty("line.separator");
							Log.i("TcpServer", "received: " + incomingMsg);
							
							if (incomingMsg.contains("SCAN_REPORT"))
							{
								//Recieved scan report - send it to broadcastScan module and close socket
								Message msg = handler.obtainMessage(1, incomingMsg);
								handler.sendMessage(msg);
								local_ss.close();
					    		local_ss = new ServerSocket(TCP_SERVER_PORT);
								
							}
							else if (incomingMsg.contains("ACK"))
							{
								accept_connections = true;
								
								//_main.update_Connected(1);
								
								Message msg = handler.obtainMessage(1, "Berez1Connected");
								handler.sendMessage(msg);
								
								transaction_listener();
							}
							else
							{
								local_ss.close();
							}
			    		}
		 	        }
				} catch (InterruptedIOException e) {
					//if timeout occurs
					e.printStackTrace();
		    	} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (local_ss != null) {
						try {
							local_ss.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
	        
	    
	        //runServer();
	 
	    }
	    
	    public void tcp_close()
	    {
	    	try
	    	{
	    		local_ss.close();
	    	
		    } catch (InterruptedIOException e) {
				//if timeout occurs
				e.printStackTrace();
	    	} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (local_ss != null) {
					try {
						local_ss.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	    }
	public void transaction_listener()
	{
		while (true)
		{
			try {
				//Wait for ACK
				String incomingMsg = in.readLine() + System.getProperty("line.separator");
				Log.i("TcpServer", "received: " + incomingMsg);
				
				if (incomingMsg.contains("ACK"))
				{
					last_recieved_ack= 1;
				}
				else//parse message and send notification
				{
					//Recieving message
				}
				
			}catch (InterruptedIOException e) {
				//if timeout occurs
				e.printStackTrace();
	    	} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//else
		//{
			//return 0;
		//}
	}

	
	public int send_synched_tcp_transaction(String message)
	{
		int res=0;
		if (accept_connections)
		{
	    	try {
				//BufferedReader in = new BufferedReader(new InputStreamReader(local_socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(local_socket.getOutputStream()));
				
				String outgoingMsg = message;//"WATERING-BEREZ-"+BerezNum + "-OFF-NOW " +System.getProperty("line.separator");
				out.write(outgoingMsg);
				out.flush();
				Log.i("TcpServer", "sent: " + outgoingMsg);
				
				//wait for the ACK
				int counter=999999999;
				while (last_recieved_ack==0 && counter!=0)
				{
					counter--;
				}
				
				if (last_recieved_ack!=0)
					//GOT ACK!
					res= 1;

				//Didnt get ACK
				last_recieved_ack = 0;	
					
				
				
			//	textDisplay.append("sent: " + outgoingMsg);
				//SystemClock.sleep(5000);
				//local_socket.close();
				//local_socket.close();
	    	} catch (InterruptedIOException e) {
				//if timeout occurs
				e.printStackTrace();
	    	} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    	
	}
		return res;
    //	accept_connections = true;
	
	}


	public void registerHandler(Handler serviceHandler) {
	    handler = serviceHandler;
	}
}