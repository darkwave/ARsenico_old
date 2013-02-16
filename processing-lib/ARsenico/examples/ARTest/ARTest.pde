import processing.video.*;
import com.mondonerd.arsenico.*;

AugmentedReality ar;

void setup() {
  size(displayWidth, displayHeight, P3D);
  
  ar = new AugmentedReality(this);
  ar.initAR(320, 240, "4x4_1.patt","4x4_2.patt","4x4_3.patt","4x4_4.patt");
  ar.initModels();
}

void draw() {
  ar.display();
}

void loadModel(java.io.File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } 
  else {
    ar.initModels(selection.getAbsolutePath());
  }
}

void mousePressed() {
  ar.toggleCamera();
}
//
//void exit() {
//  camera.quit();
// super.exit(); 
//}

void keyPressed() {
  if (key == 'l')
    selectInput("Select a file to process:", "loadModel");
}
