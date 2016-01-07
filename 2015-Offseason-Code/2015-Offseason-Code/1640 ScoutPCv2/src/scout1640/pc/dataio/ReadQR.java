package scout1640.pc.dataio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import scout1640.pc.core.Controller;
import scout1640.pc.core.ThreadKiller;
import scout1640.pc.display.SubDisplay;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class ReadQR implements ThreadKiller {
	
	private Controller controller;
	private boolean found;
	private LinkedList<BufferedImage> imageBuffer;
	private ReadQR self = this;
	private boolean killed;
	
	public ReadQR (Controller c) {
		controller = c;
		found = false;
		killed = false;
		imageBuffer = new LinkedList<BufferedImage>();
		new ScanQR();
		new FindQR();
	}
	
	@Override
	public void killThread () {
		killed = true;
	}
	
	private synchronized BufferedImage pollBuffer () {
		return imageBuffer.poll();
	}
	
	private synchronized void enqueueBuffer (BufferedImage img) {
		imageBuffer.add(img);
	}
	
	private class ScanQR implements Runnable {
		
		public ScanQR () {
			new Thread(this).start();
		}

		@Override
		public void run() {
			Mat m = new Mat();
			MatOfByte mb = new MatOfByte();
			BufferedImage img;
			VideoCapture vc = new VideoCapture(0);
			vc.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, controller.getPanelWidth());
			vc.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, controller.getPanelHeight());
			while (!found && !killed) {
				if (vc.grab()) {
					vc.read(m);
					Highgui.imencode(".png", m, mb);
					try {
						img = ImageIO.read(new ByteArrayInputStream(mb.toArray()));
						controller.displayImage(img);
						enqueueBuffer(img);
					} catch (IOException e) { }
				}
				try { Thread.sleep(20); }
				catch (InterruptedException e) { }
			}
			// "Clean up"
			vc.release();
			vc = null;
			img = null;
			mb = null;
			m = null;
			if (!killed) { controller.setState(Controller.READ_QR); }
			else { controller.setState(Controller.MAIN_SCREEN); }
		}
		
	}
	
	private class FindQR implements Runnable {
		
		public FindQR () {
			new Thread(this).start();
		}

		@Override
		public void run() {
			BufferedImage img;
			LuminanceSource lSrc;
			BinaryBitmap bmp;
			Result result;
			Reader reader = new MultiFormatReader();
			while (!found && !killed) {
				img = pollBuffer();
				if (img != null) {
					lSrc = new BufferedImageLuminanceSource(img);
					bmp = new BinaryBitmap(new HybridBinarizer(lSrc));
					try {
						result = reader.decode(bmp);
						if (result != null) { 
							found = true;
							controller.removeThreadedClass(self);
							MatchData md = MatchData.matchDataFromJSON(controller, result.toString());
							controller.printToConsole("Successfully read QR for match " + md.getMatchNumber() + ", from tablet " + md.getFieldPosition() + ". (Team " + md.getTeamNumber() + ")");
							controller.executeDataAlteringSQLQuery(md.generateSQLInsertQuery());
							controller.getDisplay().getSubDisplay(SubDisplay.DISPLAY_TEAMS).updateTeamDisplay();
						}
					} catch (NotFoundException | ChecksumException | FormatException e) { }
				}
				try { Thread.sleep(10); }
				catch (InterruptedException e) { }
			}
			// "Clean Up" (i.e. let the GC know it can clean up)
			img = null;
			lSrc = null;
			bmp = null;
			result = null;
			reader = null;
		}
		
	}

}
