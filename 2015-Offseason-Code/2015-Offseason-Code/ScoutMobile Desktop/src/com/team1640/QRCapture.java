package com.team1640;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class QRCapture implements Runnable{
	
	ScoutMobile main;
	
	QRCapture(ScoutMobile main) {
		this.main = main;
	}

	@Override
	public void run() {
		capture();
	}
	
	private void capture() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat m = new Mat();
		MatOfByte mb = new MatOfByte();
		BufferedImage img;
		
		VideoCapture camera = new VideoCapture(0);
		camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, main.getWidth());
		camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, main.getHeight());
		
		while(!main.qrScan.closed && !main.qrScan.found) {
			if (camera.grab()) {
				camera.read(m);
				Imgcodecs.imencode(".jpg", m, mb);
				try {
					img = ImageIO.read(new ByteArrayInputStream(mb.toArray()));
					main.qrScan.displayCapture(img);
					main.qrScan.enqueueBuffer(img);
				} catch (IOException e) {}
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
		
		//Clean up
		camera.release();
		camera = null;
		img = null;
		m = null;
		mb = null;
	}
	
}
