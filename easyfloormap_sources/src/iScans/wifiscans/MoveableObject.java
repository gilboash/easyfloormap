/**
 * Project:		  Easy Floor Map Locator Android APP

 * FILENAME:        MoveableObject.java

 * DESCRIPTION:    Moveable object class.
  				  can use some designing help here!!!!!
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