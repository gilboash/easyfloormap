///**********************************************************************************************************************
// 
//  Project:		  Easy Floor Map Locator Android APP
//
//  FILENAME:       PointerObject.java
//
//  DESCRIPTION:    
//
//  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
//   
//  Author: Gilboa Shveki
//
//***********************************************************************************************************************/
//
//
//
//package iScans.wifiscans;
//
//public class PointerObject extends MoveableObject{
//	
//	public int screenWidth,screenHight;
//	public int screenLeft,screenButton;
//	
//	public int SPEED = 55;
//	public final int HIGHT = 10;
//	public final int WIDTH = 50;
//	public final int MAX_TAIL = 300;
//	public final int INITIAL_TAIL = 10;
//	public final int DOT_WIDTH = 10;
//	public final int EXTEND_TAIL = 5;
//	public final int SCREEN_BORDER = 50;
//	public final int TotalScreenWidth,TotalScreenHight;
//	
//	private float paddleDestX,paddleDestY;
//	public int DIRECTION;
//	public int[] tailX,tailY;
//	private int tailIndex;
//	public int RESTART_X,RESTART_Y;
//	
//	
//	public PointerObject(int ScreenWidth,int ScreenHight)
//	{
//		//RESTART_X = x = startX;
//		//RESTART_Y = y = startY;
//		TotalScreenWidth = ScreenWidth;
//		TotalScreenHight = ScreenHight;
//		screenWidth = ScreenWidth - SCREEN_BORDER;
//		screenLeft = SCREEN_BORDER;
//		screenHight = ScreenHight - SCREEN_BORDER*2 - SCREEN_BORDER/2;
//		screenButton = SCREEN_BORDER + SCREEN_BORDER/2;
//		
//		RESTART_X = x = screenLeft;
//		RESTART_Y = y = screenHight;
//		
//		paddleDestX = ScreenWidth / 2;
//		paddleDestY = ScreenHight / 2;
//		DIRECTION = 0;//no direction
//		tailX = new int[MAX_TAIL];
//		tailY = new int[MAX_TAIL];
//		tailIndex = 0;
//		init_tail();
//		
//		
//	}
//	
//	public void setSpeed(int speed)
//	{
//		SPEED = speed;
//	}
//	
//	public int getDir()
//	{
//		return DIRECTION;
//	}
//	public void init_tail()
//	{
//		for (int i=0; i<MAX_TAIL ; i++)
//		{
//			tailX[i]=-1;
//			tailY[i]=-1;
//		}
//		PushToTail(RESTART_X,RESTART_Y);
//		for (int i=0; i<INITIAL_TAIL ; i++)
//		{
//			PushToTail(1,1);
//		}
//		
//	}
//	
//	public boolean IsSnakeInThisLoc(int xm, int ym)
//	{
//		for (int i=0; i<MAX_TAIL ; i++)
//		{
//			if (tailX[i]==-1)
//				break;
//			for (int j = xm - (DOT_WIDTH/2); j < xm + (DOT_WIDTH/2); j++)
//				for (int k = ym - (DOT_WIDTH/2); k < ym + (DOT_WIDTH/2); k++)
//					if (j == tailX[i] && k == tailY[i])
//						return true;
//		}
//		return false;
//	}
//	
//	public void restart()
//    {
//        x = RESTART_X;
//        y = RESTART_Y;
//        tailIndex = 0;
//        init_tail();
//        DIRECTION = 0;
//        
//    }
//	
//	public void UpdateLocationUponMove(int x, int y)
//	{
//		int i=0;
//		
//		tailX[0]=getX();
//		tailY[0]=getY();
//		
//		for (i=MAX_TAIL-1; i>1 ; i--)
//		{
//			if (tailX[i] == -1)
//				continue;
//			tailX[i]=tailX[i-1];
//			tailY[i]=tailY[i-1];
//		}
//		tailX[i] = x;
//		tailY[i] = y;
//	}
//	public boolean IsItLegalMovement(int Xloc,int Yloc)
//	{
//		for (int i=1; i<MAX_TAIL ; i++)
//		{
//			if (tailX[i] == -1)
//				break;
//			if (tailX[i] == tailX[0] && tailY[i] == tailY[0])
//				return false;
//		}
//		return true;
//	}
//	
//	public void PushToTail(int x, int y)
//	{
//		if (tailIndex >= MAX_TAIL)
//			return;
//		
//		tailX[tailIndex]=x;
//		tailY[tailIndex++]=y;
//	}
//	public void HitAnApple()
//	{
//		for (int i=0; i<EXTEND_TAIL ; i++)
//		{
//			PushToTail(1,1);
//		}
//	}
//	
//	//return the width of the paddle
//    public synchronized int getPaddleWidth()
//    {
//    	return WIDTH;
//    }
//    
//  //return the X center of the paddle
//    public synchronized int getMiddleX()
//    {
//        return x + WIDTH/2;
//    }
//    
//    //return the Y center of the paddle
//    public synchronized int getMiddleY()
//    {
//        return y + HIGHT/2;
//    }
//
//	      //synchronizing the access to the paddleDestination variable
//    public synchronized float getPaddleDestinationX()
//    {
//        return paddleDestX;
//    }
//    public synchronized float getPaddleDestinationY()
//    {
//        return paddleDestY;
//    }
//
//  //synchronizing the access to the paddleDestination variable
//    public synchronized void setPaddleDestination(float newDestinationX,float newDestinationY)
//    {
//        paddleDestX = newDestinationX;
//        paddleDestY = newDestinationY;
//        int dir;
//        if ((dir=PressedOutSideBoundaries(paddleDestX,paddleDestY)) != 0)//Meaning direction was returned
//    	{
//        	DIRECTION = dir;
//    	}
//        //For now removing the other option !!
////        else
////        {
////        	ChangeDirectionToNearest();	
////        }
//        
//        
//    }
//    
//    public int PressedOutSideBoundaries(float DestX, float DestY)
//    {
//    	if (DestX < screenLeft+50 && (DestY < screenHight+50 && DestY > screenButton-50))
//    		return 2;
//    	if (DestX > screenWidth-50 && (DestY < screenHight+50 && DestY > screenButton-50))
//    		return 1;
//    	if (DestY < screenButton + 50/* && (DestX < screenWidth && DestX > screenLeft*/)
//    		return 3;
//    	if (DestY > screenHight - 50/* && (DestX < screenWidth && DestX > screenLeft*/)
//    		return 4;
//    	return 0;
//    }
//    
//    public void ChangeDirectionToNearest()
//    {
//    	float deltaX,deltaY;
//    	int Xdir,Ydir;
//    	
//    	
//    	
//    	if (paddleDestX > getX())
//    	{
//    		deltaX = paddleDestX - getX();
//    		Xdir = 1;//right
//    	}
//		else 
//		{
//			deltaX = getX() - paddleDestX;
//    		Xdir = 2;//left
//		}
//		
//    	if (paddleDestY > getY())
//    	{
//			deltaY = paddleDestY - getY();
//			Ydir = 1;//Up
//    	}
//		else 
//		{
//			deltaY = getY()-paddleDestY;
//			Ydir = 2;//Down
//		}
//				
//		if (deltaX != 0 || deltaY != 0)
//		{
//			if (deltaX > deltaY)
//			{
//				if (Xdir == 1)
//				{
//					//Avoid moving right when direction is left
//					if (getDir() != 2)
//						DIRECTION = 1;//right
//				}
//				else 
//				{
//					//Avoid moving left when direction is right
//					if (getDir() != 1)
//						DIRECTION = 2;//Left
//				}
//			}
//			else
//			{
//				if (Ydir == 1)
//				{
//					//Avoid moving up when direction is down
//					if (getDir() != 3)
//						DIRECTION = 4;//Up
//				}
//				
//				else 
//				{
//					//Avoid moving down when direction is up
//					if (getDir() != 4)
//						DIRECTION = 3;//Down
//				}
//				
//			}
//				
//		}
//
//    }
//
//  //move the paddle to the right by single point
//    public synchronized void moveRight()
//    {
//        //check if the movement exceeds screen limits:
//        if (screenWidth > (getRight()+DOT_WIDTH/2))
//        	x+=DOT_WIDTH/2;
//            //x++;
//        else
//            //place paddle at the rightmost corner
//            x = screenWidth;
//        
//    }
//
//    //move the paddle to the left by single point
//    public synchronized void moveLeft()
//    {
//        //check if the movement exceeds screen limits:
//        if ((getLeft()-DOT_WIDTH/2) > screenLeft)
//        	x-=DOT_WIDTH/2;
//            //x--;
//        else
//            //place paddle at the leftmost corner
//            x = screenLeft;
//    }
//    
//  //move the paddle to up by single point
//    public synchronized void moveUp()
//    {
//        //check if the movement exceeds screen limits:
//        if ((getTop()-DOT_WIDTH/2) > screenButton)
//        	y-=DOT_WIDTH/2;
//            //y--;
//        else
//            //place paddle at the Upmost corner
//            y = screenButton;
//    }
//
//    //move the paddle to the right by single point
//    public synchronized void moveDown()
//    {
//        //check if the movement exceeds screen limits:
//        if (screenHight > (getBottom()+DOT_WIDTH/2))
//        	y+=DOT_WIDTH/2;
////            y++;
//        else
//            //place paddle at the rightmost corner
//            y = screenHight;
//    }
//
//    
//    
//	
//	@Override
//	public int getBottom() {
//		// TODO Auto-generated method stub
//		return y + DOT_WIDTH/2; 
//	}
//
//	@Override
//	public int getLeft() {
//		// TODO Auto-generated method stub
//		return x - DOT_WIDTH/2;
//	}
//	public int getX() {
//		// TODO Auto-generated method stub
//		return x;
//	}
//	public int getY() {
//		// TODO Auto-generated method stub
//		return y;
//	}
//
//
//	@Override
//	public int getRight() {
//		// TODO Auto-generated method stub
//		return x + DOT_WIDTH/2;
//	}
//
//	@Override
//	public int getTop() {
//		// TODO Auto-generated method stub
//		return y - DOT_WIDTH/2;
//	}
//
//	@Override
//	public void moveX() {
//		// TODO Auto-generated method stub
//		
//	}
//	public void moveY() {
//		// TODO Auto-generated method stub
//	}
//
//}
