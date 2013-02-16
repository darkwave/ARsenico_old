/**
    This file is part of ARsenico.

    ARsenico is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ARsenico is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with ARsenico.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mondonerd.arsenico;

import processing.core.*;

public class Picker 
{
  protected PApplet parent;
  public Buffer buffer;
  
  public Picker(PApplet parent) 
  {
    this.parent = parent;
    buffer = new Buffer(parent);     
    //parent.registerMethod("pre",this);
    //parent.registerMethod("draw",this);
  }      
  
  public void begin() 
  {
    if(parent.recorder == null)
    {
      parent.recorder = buffer; 
      buffer.beginDraw();           
    }
  }
  
  public void end() 
  {
    buffer.endDraw();  
    parent.recorder = null;
  }

  public void start(int i)
  {
    if (i < 0 || i > 16777214)
    {
      PApplet.println("[Picking error] start(): ID out of range");
      return;
    }
    buffer.setCurrentId(i);            
  }
  
  public void stop() 
        {
    parent.recorder = null;
  }
  
  public void resume() 
        {
    parent.recorder = buffer;
  }
  
  public int get(int x, int y)
  {
    return buffer.getId(x, y);
  }
  
  public PGraphics getBuffer()
  {
    return buffer;
  }    
}

