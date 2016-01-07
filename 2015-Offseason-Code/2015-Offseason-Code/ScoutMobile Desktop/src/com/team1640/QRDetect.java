package com.team1640;

import java.awt.image.BufferedImage;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class QRDetect implements Runnable{
	
	ScoutMobile main;
	
	BufferedImage img;
	
	boolean found;
	
	public QRDetect(ScoutMobile main) {
		this.main = main;
	}

	@Override
	public void run() {
		detect();
	}
	
	private void detect() {
		BufferedImage img;
		LuminanceSource lmsrc;
		BinaryBitmap bmp;
		Result result;
		Reader reader = new MultiFormatReader();
		while (!main.qrScan.closed && !main.qrScan.found) {
			img = main.qrScan.pollBuffer();
			if (img != null) {
				lmsrc = new RGBLuminanceSource(img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()));
				bmp = new BinaryBitmap(new HybridBinarizer(lmsrc));
				try {
					result = reader.decode(bmp);
					if (result != null) {
						main.qrScan.found = true;
						System.out.println("Found!");
					}
				} catch (NotFoundException | ChecksumException | FormatException e) { }
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) { }
		}
		
		//Clean up
		img = null;
		lmsrc = null;
		bmp = null;
		result = null;
		reader = null;
	}
}
