
/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:       Defines.java

 * DESCRIPTION:     All global sizes and API's
  				  Thats what happens when a C developer trying to write an android app :) :) :)
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

import junit.framework.Assert;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

public class Defines {
	private static final String TAG = "IndoorPlaceMe:Defines";	
	public int screenWidth,screenHight;
	public int screenLeft,screenButton;
	
	
	public final int HIGHT = 10;
	public final int WIDTH = 50;
	public final int MAX_TAIL = 300;
	public final int INITIAL_TAIL = 10;
	public final int DOT_WIDTH = 10;
	public final int EXTEND_TAIL = 5;
	public int SCREEN_BORDER = 16;//0;
	
	public int CONSTRAIN_MOVEMENT_RADIUS_IN_CELLS = 5;
	public final int REFRESH_SPEED_HIGH_FREQ = 5000;
	public final int REFRESH_SPEED_MED_FREQ  = 10000;
	public final int REFRESH_SPEED_LOW_FREQ  = 12000;
	
	public int SPEED = REFRESH_SPEED_MED_FREQ;
	
	public final String DEFAULT_MAP = "apartment_floor1.jpg";
	
	public final int X_CELL_AMOUNT_DFLT = 10;
	public final int Y_CELL_AMOUNT_DFLT = 14;
	
	public final int X_CELL_AMOUNT_MED_DFLT = 6;//7;
	public final int Y_CELL_AMOUNT_MED_DFLT = 10;//10;
	
	public final int X_CELL_AMOUNT_LOW_DFLT = 4;//5;
	public final int Y_CELL_AMOUNT_LOW_DFLT = 6;//7;
	
	
	public int X_CELL_AMOUNT = X_CELL_AMOUNT_DFLT;
	public int Y_CELL_AMOUNT = Y_CELL_AMOUNT_DFLT;
	
	public final int ACCESS_POINT_MAX = 100;//64;//32;
	
	public int ACCESS_POINT_TO_GRADE_MAX = 12;
	
	public final int RSSI_LEVELS	  = 20;
	
	public final int RSSI_MAX_LEVEL	  = -30;
	//public final int RSSI_MIN_LEVEL	  = -100;
	//public Boolean BLACK_LIST_METHOD_ALLOWED = true;
	
	public final int MAX_CELL_INDEX_LIST_FOR_AP = 30;
	public final int TotalScreenWidth,TotalScreenHight;
	
	public Boolean firstMainActivityLoaded=true;
	public int DIRECTION;
	//public float tailX,tailY;

	public int RESTART_X,RESTART_Y;
	public int x_cell_width;
	public int y_cell_hight;
	
	public int matrix_left_border,matrix_right_border,matrix_hight_border,matrix_button_border;
	public matrix_cordinates[][] matrixLocArr;
	
	public Boolean gLearningMode;
	public Boolean matrix_location_learning_requested=false;

	public Boolean	gTrackRemoteDevice = false;
	
	public int curr_matrix_x=0,curr_matrix_y=0;
	MainActivity m_activity;
	
	public double[] cell_index_grades;
	public int[] cell_index_is_graded;
	public int[] cell_index_configured;
	
	public String file_path_to_import="";
	public final double ZOOMMODE_PAN_DFLT 		= 0.5;
	public final double ZOOMMODE_ZOOM_DFLT 		= 1;
	public final int ZOOMMODE_DFLT 		= 0;
	public final int ZOOMMODE_UPRIGHT 	= 1;
	public final int ZOOMMODE_UPLEFT 	= 2;
	public final int ZOOMMODE_LOWRIGHT 	= 3;
	public final int ZOOMMODE_LOWLEFT 	= 4;
	public int ZoomMode=ZOOMMODE_DFLT;//0-default,1-upperright,2-upperleft,3-lower-right,4-lowerleft
	
	public Defines(Display defaultdisplay,int defaultdisplayagain,MainActivity mainact)
	{
		
		int ScreenWidth;
		int ScreenHight;
		
		
		
		 if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB){                   
			 Point size = new Point();
				defaultdisplay.getSize(size);
				ScreenWidth = size.x;
				ScreenHight = size.y;
		    }
		    else{
		    	ScreenWidth = defaultdisplay.getWidth();
				ScreenHight = defaultdisplay.getHeight();

		    }
		
		
		
		m_activity = mainact;
		gLearningMode = false;
		TotalScreenWidth = ScreenWidth;
		TotalScreenHight = ScreenHight;
		
		SCREEN_BORDER = ScreenHight / 20;
		
		screenWidth = ScreenWidth - SCREEN_BORDER/2;
		screenLeft = SCREEN_BORDER/2;
		
		screenHight = SCREEN_BORDER/4;
		screenButton = ScreenHight - SCREEN_BORDER*5;//ScreenHight - SCREEN_BORDER*3;//; - SCREEN_BORDER/2;
		
		init_matrix_values(X_CELL_AMOUNT_DFLT,Y_CELL_AMOUNT_DFLT);

	
		Log.d(TAG,"Defines created, x_cell_width "+ x_cell_width+" y_cell_hight "+y_cell_hight);

	//	init_tail();
//		tailX=TotalScreenWidth/2;
//		tailY=TotalScreenHight/2;
		
		Log.d(TAG,"Sizes has been set, total width "+ScreenWidth+" Total hight "+ScreenHight+" matrix width "+screenWidth +" matrix button "+screenButton);
	}
	
	public void init_matrix_values(int x_cells_number, int y_cells_numbmer)
	{
		//Matrix params
		
		X_CELL_AMOUNT = x_cells_number;
		Y_CELL_AMOUNT = y_cells_numbmer;
		
		curr_matrix_x=0;
		curr_matrix_y=0;
		
		x_cell_width = (screenWidth - screenLeft) / X_CELL_AMOUNT;
		y_cell_hight = (screenButton - screenHight) / Y_CELL_AMOUNT;
		
		
		matrix_left_border = screenLeft;
		matrix_right_border = screenLeft+X_CELL_AMOUNT*(x_cell_width);
		matrix_button_border = screenButton;
		matrix_hight_border = screenButton-Y_CELL_AMOUNT*(y_cell_hight);
		
		
		matrixLocArr = new matrix_cordinates[X_CELL_AMOUNT][Y_CELL_AMOUNT];
		//Set matrix loc boundaries
		int i,j;
		for (i=0; i<X_CELL_AMOUNT; i++)
		{
			for (j=0;j<Y_CELL_AMOUNT; j++)
			{
				matrixLocArr[i][j] = new matrix_cordinates();
				matrixLocArr[i][j].set_left_x_border(matrix_left_border+(i)*x_cell_width);
				matrixLocArr[i][j].set_right_x_border(matrix_left_border+(i+1)*x_cell_width);
				matrixLocArr[i][j].set_high_y_border(matrix_hight_border+(j)*y_cell_hight);
				matrixLocArr[i][j].set_low_y_border(matrix_hight_border+(j+1)*y_cell_hight);
								
			}
		}
		
		init_cell_index_grades_array();
		init_cell_index_configured_array();
	}
	
	
	public void grade_cell_index_for_val_diff(int cell_index,double grade)
	{
		cell_index_is_graded[cell_index]++;
		cell_index_grades[cell_index]+= (grade);//(RSSI_LEVELS - val_diff);
		
		//DEBUG - Log.d(TAG,"grade-cell-index "+cell_index+" with grade "+grade + " graded "+ cell_index_is_graded[cell_index] + "times");
	}
	
	public void mark_cell_configured(int cell_index)
	{
		cell_index_configured[cell_index]=1;
	}
	
	public Boolean is_cell_configured(int cell_index)
	{
		return ((cell_index_configured[cell_index]==1) ? true : false);
	}
	
	public void init_cell_index_grades_array()
	{
    	cell_index_grades 	  = new double[get_maximum_cell_index()];
    	cell_index_is_graded 	  = new int[get_maximum_cell_index()];
    	
    	for (int i=0;i<get_maximum_cell_index();i++)
    	{
    		cell_index_grades[i]=0;
    		cell_index_is_graded[i]=0;
    	}
	}
	
	public void init_cell_index_configured_array()
	{
		cell_index_configured = new int[get_maximum_cell_index()];
    	
    	for (int i=0;i<get_maximum_cell_index();i++)
    	{
    		cell_index_configured[i]=0;
    	}
	}
	
	public void setSpeed(int speed)
	{
		SPEED = speed;
	}
	
	

	
	public int zoomInWhereMatrixCor(int mat_x,int mat_y)
	{
		if ((mat_x>=(X_CELL_AMOUNT/2))&&(mat_y>=(Y_CELL_AMOUNT/2)))
		{
			return ZOOMMODE_LOWRIGHT;
		}
		else if ((mat_x>=(X_CELL_AMOUNT/2))&&(mat_y<(Y_CELL_AMOUNT/2)))
		{
			return ZOOMMODE_UPRIGHT;
		}
		else if ((mat_x<(X_CELL_AMOUNT/2))&&(mat_y<(Y_CELL_AMOUNT/2)))
		{
			return ZOOMMODE_UPLEFT;
		}
		else
			return ZOOMMODE_LOWLEFT;
	}
	
	public int zoomInWhere(float x,float y)
	{
		if ((x>(TotalScreenWidth/2))&&(y>(TotalScreenHight/2)))
		{
			return ZOOMMODE_LOWRIGHT;
		}
		else if ((x>(TotalScreenWidth/2))&&(y<(TotalScreenHight/2)))
		{
			return ZOOMMODE_UPRIGHT;
		}
		else if ((x<(TotalScreenWidth/2))&&(y<(TotalScreenHight/2)))
		{
			return ZOOMMODE_UPLEFT;
		}
		else
			return ZOOMMODE_LOWLEFT;
	}
	
	public void setNewLoc(float x,float y)
	{
	//	tailX=x;
	//tailY=y;
		
		
		curr_matrix_y = locate_matrix_y_cell_from_y_value(y);
		curr_matrix_x = locate_matrix_x_cell_from_x_value(x);
		if (gLearningMode == true && curr_matrix_x != -1 && curr_matrix_y != -1)
		{//Meaning we are in learning mode AND new location to learn is set
			matrix_location_learning_requested = true;
			
			Toast.makeText(m_activity, "Learning location, please hold...", Toast.LENGTH_SHORT).show();

			Log.d(TAG,"Learning mode, x="+curr_matrix_x+" y="+curr_matrix_y);
		}
		
	}

	//Set matrix X and Y directly
	public void setNewMatrix_X_and_Y(int i,int j)
	{
			
				curr_matrix_y = j;
				curr_matrix_x = i;
	}

	
//	public float getX()
//	{
//		return tailX;
//	}
//	
//	public float getY()
//	{
//		return tailY;
//	}
	
	public int locate_matrix_x_cell_from_x_value(float x_value)
	{
		if (x_value < matrix_left_border)
			return -1;
		
		int i=0;
		for (i=0;i<X_CELL_AMOUNT;i++)
		{
			if (x_value >= matrixLocArr[i][0].get_left_x_border() && x_value <= matrixLocArr[i][0].get_right_x_border())
			{
				return i;
			}
		}
				
		return -1;
		
	}
	
	public int locate_matrix_y_cell_from_y_value(float y_value)
	{
	
		if (y_value < matrix_hight_border)
			return -1;
		
		for (int j=0;j<Y_CELL_AMOUNT;j++)
		{
			if (y_value >= matrixLocArr[0][j].get_high_y_border() && y_value <= matrixLocArr[0][j].get_low_y_border())
			{
				return j;
			}
		}
		
		return -1;
		
		
	}
	
	public int get_maximum_cell_index()
	{
		return ((X_CELL_AMOUNT*Y_CELL_AMOUNT));
	}
	
	public int cell_i_j_to_index(int i, int j)
	{
		return (j*X_CELL_AMOUNT+i);
	}
	
	public int cell_index_to_j(int cell_index)
	{
//		int j=0,multiplier=0;
//		
//		if (cell_index==0)
//			return j;
//		
//		while (multiplier<cell_index)
//		{
//			j++;
//			multiplier = j*X_CELL_AMOUNT;
//		}
//		return (j-1);
		return (cell_index / X_CELL_AMOUNT);
	}
	
	public int cell_index_to_i(int cell_index)
	{
//		int j=0,multiplier=0;
//		
//		if (cell_index==0)
//			return j;
//		
//		while (multiplier<cell_index)
//		{
//			j++;
//			multiplier = j*X_CELL_AMOUNT;
//		}
//		return (cell_index - ((j-1)*X_CELL_AMOUNT));
		return (cell_index - ((cell_index_to_j(cell_index))*X_CELL_AMOUNT));
	}

	
	}
