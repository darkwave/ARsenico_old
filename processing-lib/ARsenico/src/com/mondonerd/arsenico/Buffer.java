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
import processing.opengl.*;
public class Buffer extends PGraphics3D
{
  protected int current_color = 0;
    
  public Buffer(PApplet parent)
  {
    setParent(parent);
    setSize(parent.width, parent.height);    
  }

  @Override
  public boolean displayable() { return true; }
  
  public void callCheckSettings() { super.checkSettings(); }
  
  @Override
  public void lights() {}
  
  @Override
  public void smooth() {}
  
  @Override
  public void fill(int arg) {}
  
  @Override
  public void fill(float arg) {}
  
  @Override
  public void fill(float arg, float arg_1) {}
  
  @Override
  public void fill(int arg, float arg_1) {}
  
  @Override
  public void fill(float arg, float arg_1, float arg_2) {}
  
  @Override
  public void fill(float arg, float arg_1, float arg_2, float arg_3) {}
  
  @Override
  public void stroke(int arg) {}
  
  @Override
  public void stroke(float arg) {}
  
  @Override
  public void stroke(float arg, float arg_1) {}
  
  @Override
  public void stroke(int arg, float arg_1) {}
  
  @Override
  public void stroke(float arg, float arg_1, float arg_2) {}
  
  @Override
  public void stroke(float arg, float arg_1, float arg_2, float arg_3) {}
  
  @Override
  public void textureMode(int arg) {}
  
  @Override
  public void texture(PImage arg) {}
  
  @Override
  public void vertex(float x, float y, float z, float u, float v) { super.vertex(x, y, z); }
  
  @Override
  public void image(PImage arg, float arg_1, float arg_2) {}
  
  @Override
  public void image(PImage arg, float arg_1, float arg_2, float arg_3, float arg_4) {}
  
  @Override
  public void image(PImage arg, float arg_1, float arg_2, float arg_3, float arg_4, int arg_5, int arg_6, int arg_7, int arg_8) {}
  
  @Override
  protected void imageImpl(PImage image, float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2) {}
  
  public void setCurrentId(int i) 
  {
      // ID 0 to 16777214  => COLOR -16777215 to -1 (white)
      // -16777216 is black
      current_color = i - 16777215;
      super.fill(current_color);      
  }
  
  public int getId(int x, int y)
  {
      super.loadPixels();
      // COLOR -16777216 (black) to -1 => ID -1 (no object) to 16777214 
      int id = pixels[y*width+x] + 16777215;
      return id;
  }
}

