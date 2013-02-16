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

/**
 * @author Massimo Avvisati <dw@mondonerd.com>
 *
 */

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PMatrix3D;
import processing.core.PShape;
import jp.nyatla.nyar4psg.*;

public class AugmentedReality {

	MultiMarker nya;

	PGraphics offscreen;
	PGraphics colorPicker;
	PApplet parent;

	PShape[] scene;

	// TODO move markers filenames to XML file
	String defaultMarkerFilename = "4x4_1.patt";

	int numberOfMarkers;

	PShape placeHolder;
	CameraConfiguration camera;
	Picker picker;

	public AugmentedReality(PApplet _parent) {
		parent = _parent;
		colorPicker = parent.createGraphics(parent.width, parent.height,
				PApplet.P3D);
		picker = new Picker(parent);
		camera = new CameraConfiguration(parent);
		camera.init();
		camera.plug(this);
	}
	
	public void quit() {
		camera.quit();	
	}
	
	

	public void initModels(String... filenames) {
		placeHolder = parent.createShape(PApplet.BOX, 80, 80, 80);
		placeHolder.translate(0, 0, 40);
		placeHolder.fill(255, 0, 0);
		placeHolder.noStroke();

		if (filenames.length > 0) {
			scene = new PShape[filenames.length];

			for (int i = 0; i < filenames.length; i++) {
				PShape loadedShape = parent.loadShape(filenames[i]);
				if (loadedShape != null) {
					scene[i] = parent.createShape(PApplet.GROUP);
					scene[i].addChild(loadedShape);
					scene[i].rotateX(PApplet.radians(90));
					scene[i].scale(4, 4, 4);
				}
			}
		}
	}

	private void setPerspective(PMatrix3D i_projection, PGraphics pg) {
		// Projection frustum
		float far = i_projection.m23 / (i_projection.m22 + 1);
		float near = i_projection.m23 / (i_projection.m22 - 1);
		pg.frustum((i_projection.m02 - 1) * near / i_projection.m00,
				(i_projection.m02 + 1) * near / i_projection.m00,
				(i_projection.m12 - 1) * near / i_projection.m11,
				(i_projection.m12 + 1) * near / i_projection.m11, near, far);
		return;
	}

	private void displayAR(PGraphics pg, boolean off) {
		if (off)
			pg.beginDraw();

		pg.perspective();
		if (!off) {
			pg.lights();
			pg.hint(PApplet.DISABLE_DEPTH_TEST);
			pg.image(offscreen, 0, 0, parent.width, parent.height);
		} else {
			pg.noStroke();
			pg.ambientLight(255, 255, 255);
			pg.background(0);
		}
		pg.hint(PApplet.ENABLE_DEPTH_TEST);
		pg.pushMatrix();
		PMatrix3D m = nya.getProjectionMatrix().get();
		pg.setMatrix(m);
		setPerspective(m, pg);

		for (int i = 0; i < numberOfMarkers; i++) {
			if (nya.isExistMarker(i)) {
				PMatrix3D transf = nya.getMarkerMatrix(i);
				pg.pushMatrix();
				pg.setMatrix(transf); // load Marker matrix
				if (scene != null && i < scene.length && scene[i] != null)
					pg.shape(scene[i], 0, 0);
				else
					pg.shape(placeHolder, 0, 0);
				pg.popMatrix();
			}
		}
		pg.popMatrix();

		if (off)
			pg.endDraw();
	}

	public void display() {
		camera.update();
		displayAR(parent.g, false);
		displayAR(colorPicker, true);
	}
	
	public void toggleCamera() {
		camera.toggle();
	}

	public void initAR(int w, int h, String... markersFilenames) {
		offscreen = parent.createGraphics(w, h, PApplet.P2D);
		offscreen.beginDraw();
		offscreen.background(0);
		offscreen.endDraw();
		nya = new MultiMarker(parent, w, h, "camera_para.dat",
				NyAR4PsgConfig.CONFIG_PSG);
		// add markers
		if (markersFilenames.length <= 0) {
			nya.addARMarker(defaultMarkerFilename, 80);
			numberOfMarkers = 1;
		} else {
			numberOfMarkers = markersFilenames.length;
			for (int i = 0; i < numberOfMarkers; i++) {
				nya.addARMarker(markersFilenames[i], 80);
			}
		}
	}

	protected void refreshAR(PImage inputImg) {
		offscreen.beginDraw();
		offscreen.image(inputImg, 0, 0, offscreen.width, offscreen.height);
		offscreen.endDraw();
		nya.detect(offscreen);
	}
}
