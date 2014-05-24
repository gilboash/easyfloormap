
/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       LocatorView.java

  DESCRIPTION:    need to be removed................................

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/


package iScans.wifiscans;




import android.view.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import android.graphics.Rect;

public class LocatorView extends View {

//OPTIMIZE - REMOVE THIS CLASS!!!
	private Paint HeadPaddlePaint;
	private Defines dDefines;
	
	
	public LocatorView(Context context,Defines defin) {
		super(context);
		dDefines = defin;
	}

	//returns the paint that should be used to paint the paddle
    private Paint getHeadPaddlePaint()
    {
        if (HeadPaddlePaint == null)
        {
        	HeadPaddlePaint = new Paint();
        	HeadPaddlePaint.setStrokeWidth(dDefines.DOT_WIDTH);
        	HeadPaddlePaint.setColor(Color.GRAY);

        }
        return HeadPaddlePaint;
    }
    
  //do the actual drawing itself
    public void onDraw(Canvas canvas)
    {
//        canvas.drawCircle(dDefines.getX(), dDefines.getY(), dDefines.DOT_WIDTH,getHeadPaddlePaint());
    }
    
    
  
    public void translateRSSI2Location(String SSID[])
    {
    	
    }


}
