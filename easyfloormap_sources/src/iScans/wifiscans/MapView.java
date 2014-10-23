/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:        MapView.java

 * DESCRIPTION:    Map view - for mapping and for locating modes.
  				  can use some designing help here!!!
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import android.graphics.Color;
import android.graphics.Paint;

public class MapView extends View {
	  private static final String TAG = "MapView";
	//private BallView ballV;
private Defines gDefines;
	private Bitmap backGroundBitmap,markerBitmap,locatemeBitmap;
	private Paint TextPaintB,matrixpaint,rectpaing,rectLocatedpaing,textpaint;
	//private LocatorView locationView;
	private Rect rectangle;
	private MainActivity mainact;
	private ssid_rssi_prms ssidRssiPrms;
	
	/** Paint object used when drawing bitmap. */
    private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
	private Rect mRectSrc,mRectDst;
	
	public MapView(Context context,Defines defin,MainActivity mainac,ssid_rssi_prms sprms) {
		super(context);
		mainact = mainac;
		ssidRssiPrms = sprms;
		//locationView = locView;
		// TODO Auto-generated constructor stub
//		set_new_background_map_resource(R.drawable.apartment_floor1);
				
		backGroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apartment_floor1);
		locatemeBitmap   = BitmapFactory.decodeResource(getResources(), R.drawable.center);
		markerBitmap     = BitmapFactory.decodeResource(getResources(), R.drawable.pinpoint2);
				
		gDefines = defin;
		rectangle = new Rect();
		
		/** Rectangle used (and re-used) for cropping source image. */
	    mRectSrc = new Rect();

	    /** Rectangle used (and re-used) for specifying drawing area on canvas. */
	    mRectDst = new Rect();

		
		ssidRssiPrms.set_map_view_when_u_can(this);
	}
	
	public String save_bitmap_image_locally(String file_name)
	{
		String file_path="";
		try
		{
			file_path = Environment.getExternalStorageDirectory().getPath() + "/wifilocatormaps/zz_maps/"+file_name;
			FileOutputStream fOut= new FileOutputStream(file_path); 
			// Here path is either sdcard or internal storage
			backGroundBitmap.compress(Bitmap.CompressFormat.JPEG,100,fOut);
			fOut.flush();
			fOut.close();
		}
		catch (Exception e)
		{
			file_path = "";
			Toast.makeText(mainact, "Failed to save the background image "+file_name, Toast.LENGTH_SHORT).show();
		}
		return file_path;
	}
	
	public void set_new_background_map_stream(InputStream stream)
	{
		Bitmap new_backGroundBitmap = BitmapFactory.decodeStream(stream);
		backGroundBitmap = Bitmap.createScaledBitmap(new_backGroundBitmap, gDefines.screenWidth, gDefines.screenButton, true);
		
		
	}

	public void set_new_marker_bitmap(int multiplier)
	{
		Bitmap new_markerBitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.pinpoint2);				
		markerBitmap = Bitmap.createScaledBitmap(new_markerBitmap, gDefines.x_cell_width*multiplier,gDefines.y_cell_hight*multiplier, true); 
		
	}
	
	
	public void set_new_background_map_filepath(String path_name)
	{
		String file_name="";
		String[] separated = path_name.split("/");
		for (int j=0;j<separated.length;j++)
		{
			if (separated[j].contains("jpg"))
			{
				file_name=separated[j];
				break;
			}
		}
		
		if (! file_name.contains("jpg"))
		{
			Toast.makeText(mainact, "havent found jpg in the image path", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String file_path = Environment.getExternalStorageDirectory().getPath() + "/wifilocatormaps/zz_maps/"+file_name;
		Bitmap new_backGroundBitmap = BitmapFactory.decodeFile(file_path);
		backGroundBitmap = Bitmap.createScaledBitmap(new_backGroundBitmap, gDefines.screenWidth, gDefines.screenButton, true);
	}
	
	private Paint getTextPaintBigger()
    {
        if (TextPaintB == null)
        {
        	TextPaintB = new Paint();
        	TextPaintB.setTextScaleX(2);
        	TextPaintB.setStrokeWidth(100);
        	TextPaintB.setColor(Color.BLUE);

        }
        return TextPaintB;
    }

	private Paint getMatrixLinePaint()
    {
        if (matrixpaint == null)
        {
        	matrixpaint = new Paint();
        	matrixpaint.setTextScaleX(2);
        	matrixpaint.setStrokeWidth(2);
        	matrixpaint.setColor(Color.BLUE);

        }
        return matrixpaint;
    }

	private Paint getTextPaint()
    {
        if (textpaint == null)
        {
        	textpaint = new Paint();
        	textpaint.setTextScaleX(2);
        	textpaint.setStrokeWidth(2);
        	textpaint.setColor(Color.GRAY);

        }
        return textpaint;
    }

	
	
	private Paint getRectPaint()
    {
        if (rectpaing == null)
        {
        	rectpaing = new Paint();
        	rectpaing.setTextScaleX(2);
        	rectpaing.setStrokeWidth(2);
        	rectpaing.setColor(Color.YELLOW);

        }
        return rectpaing;
    }
	
	private Paint getRectLocatedPaint()
    {
        if (rectLocatedpaing == null)
        {
        	rectLocatedpaing = new Paint();
        	rectLocatedpaing.setTextScaleX(2);
        	rectLocatedpaing.setStrokeWidth(2);
        	rectLocatedpaing.setColor(Color.RED);
        	rectLocatedpaing.setARGB(180, 100, 100, 0);

        }
        return rectLocatedpaing;
    }
	
	
//	public void setViews(/*BallView view,*/SnakeView snakeview,GameLevelView gamelev,AppleView appView)
	public void setViews()
	{
//		snakeV = snakeview;
//		gameV = gamelev;
//		appleV = appView;
//		/*ballV = view;
//		paddV = padview;*/
	}
	
	private int matrix_cell_multiplier=1,matrix_cell_offset_x=0,matrix_cell_offset_y=0;
	public void prepare_zoom_rectangles(int zoomMode)
	{
		
		double pX = gDefines.ZOOMMODE_PAN_DFLT;
		double pY = gDefines.ZOOMMODE_PAN_DFLT;
		double zX = gDefines.ZOOMMODE_ZOOM_DFLT;
		double zY = gDefines.ZOOMMODE_ZOOM_DFLT;
		switch (zoomMode)
		{
			case 0://ZOOMMODE_DFLT
				pX = gDefines.ZOOMMODE_PAN_DFLT;
				pY = gDefines.ZOOMMODE_PAN_DFLT;
				zX = gDefines.ZOOMMODE_ZOOM_DFLT;
				zY = gDefines.ZOOMMODE_ZOOM_DFLT;
				matrix_cell_multiplier=1;
				matrix_cell_offset_x=0;
				matrix_cell_offset_y=0;
				break;
			case 1://ZOOMMODE_UPRIGHT
				pX = gDefines.ZOOMMODE_PAN_DFLT + 0.25;
				pY = gDefines.ZOOMMODE_PAN_DFLT - 0.25;
				zX = gDefines.ZOOMMODE_ZOOM_DFLT *2;
				zY = gDefines.ZOOMMODE_ZOOM_DFLT *2;
				matrix_cell_multiplier=2;
				matrix_cell_offset_x=gDefines.X_CELL_AMOUNT/2;
				matrix_cell_offset_y=0;
				break;
			case 2://ZOOMMODE_UPLEFT
				pX = gDefines.ZOOMMODE_PAN_DFLT - 0.25;
				pY = gDefines.ZOOMMODE_PAN_DFLT - 0.25;
				zX = gDefines.ZOOMMODE_ZOOM_DFLT *2;
				zY = gDefines.ZOOMMODE_ZOOM_DFLT *2;
				matrix_cell_multiplier=2;
				matrix_cell_offset_x=0;
				matrix_cell_offset_y=0;
				break;
			case 3://ZOOMMODE_LOWRIGHT
				pX = gDefines.ZOOMMODE_PAN_DFLT + 0.25;
				pY = gDefines.ZOOMMODE_PAN_DFLT + 0.25;
				zX = gDefines.ZOOMMODE_ZOOM_DFLT *2;
				zY = gDefines.ZOOMMODE_ZOOM_DFLT *2;
				matrix_cell_multiplier=2;
				matrix_cell_offset_x=gDefines.X_CELL_AMOUNT/2;
				matrix_cell_offset_y=gDefines.Y_CELL_AMOUNT/2;
				break;
			case 4://ZOOMMODE_LOWLEFT
				pX = gDefines.ZOOMMODE_PAN_DFLT - 0.25;
				pY = gDefines.ZOOMMODE_PAN_DFLT + 0.25;
				zX = gDefines.ZOOMMODE_ZOOM_DFLT *2;
				zY = gDefines.ZOOMMODE_ZOOM_DFLT *2;
				matrix_cell_multiplier=2;
				matrix_cell_offset_x=0;
				matrix_cell_offset_y=gDefines.Y_CELL_AMOUNT/2;
				break;
		}
		final int viewWidth = getWidth();
        final int viewHeight = getHeight();
        final int bitmapWidth = backGroundBitmap.getWidth();
        final int bitmapHeight = backGroundBitmap.getHeight();

        final double panX = pX;//0.25;def-0.5
        final double panY = pY;//0.25;def-0.5
        final double zoomX = zX;//2;def-1
        final double zoomY = zY;//2;def-1
        
     // Setup source and destination rectangles
        mRectSrc.left = (int)(panX * bitmapWidth - viewWidth / (zoomX * 2));
        mRectSrc.top = (int)(panY * bitmapHeight - viewHeight / (zoomY * 2));
        mRectSrc.right = (int)(mRectSrc.left + viewWidth / zoomX);
        mRectSrc.bottom = (int)(mRectSrc.top + viewHeight / zoomY);
        mRectDst.left = getLeft();
        mRectDst.top = getTop();
        mRectDst.right = getRight();
        mRectDst.bottom = getBottom();

        // Adjust source rectangle so that it fits within the source image.
        if (mRectSrc.left < 0) {
            mRectDst.left += -mRectSrc.left * zoomX;
            mRectSrc.left = 0;
        }
        if (mRectSrc.right > bitmapWidth) {
            mRectDst.right -= (mRectSrc.right - bitmapWidth) * zoomX;
            mRectSrc.right = bitmapWidth;
        }
        if (mRectSrc.top < 0) {
            mRectDst.top += -mRectSrc.top * zoomY;
            mRectSrc.top = 0;
        }
        if (mRectSrc.bottom > bitmapHeight) {
            mRectDst.bottom -= (mRectSrc.bottom - bitmapHeight) * zoomY;
            mRectSrc.bottom = bitmapHeight;
        }
        
        gDefines.ZoomMode = zoomMode;
	}
	
	public void onDraw(Canvas canvas) {
		
		
		

		//canvas.drawBitmap(backGroundBitmap, 0,0, null);
		int curr_x = gDefines.curr_matrix_x;
		int curr_y = gDefines.curr_matrix_y;
		int mplier=1;
		if (gDefines.ZoomMode!=0 && curr_x!=-1)
		{
			int zoom_loc=gDefines.zoomInWhereMatrixCor(gDefines.curr_matrix_x,gDefines.curr_matrix_y);
			
			prepare_zoom_rectangles(zoom_loc);
			curr_x = (gDefines.curr_matrix_x-matrix_cell_offset_x)*matrix_cell_multiplier;
			curr_y = (gDefines.curr_matrix_y-matrix_cell_offset_y)*matrix_cell_multiplier;
			mplier=2;
			
		}
		
		
		//prepare_zoom_rectangles();
		canvas.drawBitmap(backGroundBitmap, mRectSrc, mRectDst, mPaint);
		
		
		
		
//		canvas.drawText(" Configure HotSpots ->", gDefines.screenWidth - 210, gDefines.screenHight-gDefines.DOT_WIDTH/2 -30, getTextPaintBigger());
		
		setKeepScreenOn(true);
		
	
		
//		if (gDefines.gLearningMode==true)
//		{
//			canvas.drawText("Save Location", gDefines.screenLeft, gDefines.screenHight-gDefines.DOT_WIDTH/2 -30, getTextPaintBigger());
//			canvas.drawText("Learning Mode", gDefines.screenWidth-180,gDefines.screenButton+25,getTextPaint());
//		} else
//		{
//			canvas.drawText("Load Map", gDefines.screenLeft, gDefines.screenHight-gDefines.DOT_WIDTH/2 -30, getTextPaintBigger());
//			canvas.drawText("Locate Me Mode", gDefines.screenWidth-180,gDefines.screenButton+25,getTextPaint());
//		}

		
		
		//canvas.drawText(" "+gDefines.curr_matrix_x + " " + gDefines.curr_matrix_y, gDefines.screenWidth - 210, gDefines.screenHight-gDefines.DOT_WIDTH/2 -30, getTextPaintBigger());
		//locationView.onDraw(canvas);
		
		if (!gDefines.gLearningMode )
		{
			
			canvas.drawBitmap(locatemeBitmap, (gDefines.matrix_right_border - gDefines.x_cell_width), gDefines.matrix_button_border-gDefines.y_cell_hight, mPaint);
			
//			rectangle.left = gDefines.matrixLocArr[gDefines.curr_matrix_x][gDefines.curr_matrix_y].left_x_border;
//			rectangle.right = gDefines.matrixLocArr[gDefines.curr_matrix_x][gDefines.curr_matrix_y].right_x_border;
//			rectangle.top = gDefines.matrixLocArr[gDefines.curr_matrix_x][gDefines.curr_matrix_y].high_y_border;
//			rectangle.bottom = gDefines.matrixLocArr[gDefines.curr_matrix_x][gDefines.curr_matrix_y].low_y_border;
			if (curr_x !=-1 && curr_y!=-1)
			{
				set_new_marker_bitmap(mplier);	
				canvas.drawBitmap(markerBitmap, 
						gDefines.matrixLocArr[curr_x][curr_y].left_x_border,
						gDefines.matrixLocArr[curr_x][curr_y].high_y_border, null);
			}
						
		}
		else
		{
			//Draw the matrix
			for (int i=0; i<=gDefines.Y_CELL_AMOUNT;i++)
			{
				canvas.drawLine(gDefines.matrix_left_border, gDefines.matrix_button_border-i*(gDefines.y_cell_hight), gDefines.matrix_right_border, gDefines.matrix_button_border-i*(gDefines.y_cell_hight), getMatrixLinePaint());
			}
			
			for (int j=0; j<=gDefines.X_CELL_AMOUNT;j++)
			{
				canvas.drawLine(gDefines.matrix_left_border+j*(gDefines.x_cell_width), gDefines.matrix_button_border, gDefines.matrix_left_border+j*(gDefines.x_cell_width), gDefines.matrix_hight_border, getMatrixLinePaint());
			}
			
			
			
			
			//Mark the already-configured cells
			for (int i=0;i<gDefines.get_maximum_cell_index();i++)
			{
				if (gDefines.cell_index_configured[i]==1)
				{
			    	int x= gDefines.cell_index_to_i(i);
			    	int y= gDefines.cell_index_to_j(i);

					build_rectangle_for_matrix_x_y(x,y);	
					canvas.drawRect(rectangle, getRectLocatedPaint());
				}
				
			}
			
			//Mark the current learned cell
			if (gDefines.curr_matrix_x != -1 && gDefines.curr_matrix_y != -1)
			{
				build_rectangle_for_matrix_x_y(gDefines.curr_matrix_x,gDefines.curr_matrix_y);
				canvas.drawRect(rectangle, getRectPaint());
			}
				
		//canvas.drawCircle( gDefines.screenWidth, gDefines.screenHight+50, gDefines.DOT_WIDTH * 3 /4,getMatrixLinePaint());
		}
		
	}
	
	public void build_rectangle_for_matrix_x_y(int x, int y)
	{
		rectangle.left = gDefines.matrixLocArr[x][y].left_x_border;
		rectangle.right = gDefines.matrixLocArr[x][y].right_x_border;
		rectangle.top = gDefines.matrixLocArr[x][y].high_y_border;
		rectangle.bottom = gDefines.matrixLocArr[x][y].low_y_border;

	}
}
