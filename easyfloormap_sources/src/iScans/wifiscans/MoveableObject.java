/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       MoveableObject.java

  DESCRIPTION:    Moveable object class.
  				  can use some designing help here!!!!!

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/


package iScans.wifiscans;

public abstract class MoveableObject {
	protected int x; //current horizontal position of the center of the object
    protected int y; //current vertical position of the center of the object
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public abstract int getLeft();
    public abstract int getRight();
    public abstract int getTop();
    public abstract int getBottom();
    public abstract void moveX();
    public abstract void moveY();
}