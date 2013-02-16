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

import processing.core.PApplet;
import processing.video.*;

/**
 * @author Massimo Avvisati <dw@mondonerd.com>
 * 
 */
class CameraConfiguration {

	// TODO move configuration to XML file
	String favoriteCamera = "/dev/video1";
	String favoriteResolution = "320x240";

	Capture cam;
	boolean cameraStarted = false;
	PApplet parent;
	AugmentedReality ar;

	protected CameraConfiguration(PApplet _parent) {
		parent = _parent;
	}

	protected CameraConfiguration(PApplet _parent, String _favoriteCamera,
			String _favoriteResolution) {
		parent = _parent;
		favoriteCamera = _favoriteCamera;
		favoriteResolution = _favoriteResolution;
	}

	protected void plug(AugmentedReality _ar) {
		ar = _ar;
	}

	public void update() {
		if (cam.available()) {
			onCameraPreviewEvent();
		}
	}

	void onCameraPreviewEvent() {
		cam.read();
		if (ar != null)
			ar.refreshAR(cam);
	}

	protected void init() {
		String[] cameras = Capture.list();

		if (cameras.length == 0) {
			PApplet.println("There are no cameras available for capture.");
			parent.exit();
		} else {
			int selectedCam = -1;
			int secondChoice = 0;

			for (int i = 0; i < cameras.length; i++) {
				if (cameras[i].indexOf(favoriteCamera) != -1
						&& cameras[i].indexOf(favoriteResolution) != -1) {
					selectedCam = i;
					PApplet.println("Favorite camera selected: " + cameras[i]);
					break;
				} else if (cameras[i].indexOf(favoriteCamera) == -1
						&& cameras[i].indexOf(favoriteResolution) != -1) {
					secondChoice = i;
//					parent.println("Second choice by resolution selected: "
//							+ cameras[i]);
				}
			}

			// The camera can be initialized directly using an element
			// from the array returned by list():
			// cam = new Capture(parent, 640, 480, "/dev/video1", 30);
			if (selectedCam == -1)
				selectedCam = secondChoice;

			PApplet.println("Selected camera:" + cameras[selectedCam]);
			cam = new Capture(parent, cameras[selectedCam]);
		}
	}

	public void quit() {
		if (cameraStarted) {
			cameraStarted = false;
			cam.stop();
			PApplet.println("camera stopped");
		}
	}

	public void toggle() {
		if (cameraStarted) {
			cameraStarted = false;
			cam.stop();
		} else {
			cameraStarted = true;
			cam.start();
		}
	}
}
