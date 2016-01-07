package com.team1640;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class QRScan extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	
	ScoutMobile main;
	
	ScanPanel scanPanel;
	
	private LinkedList<BufferedImage> imageBuffer;
	
	boolean closed, found;
	
	public QRScan(ScoutMobile main) {
		super("Scan QR Code");
		
		this.main = main;
		
		closed = false;
		found = false;
	}
	
	public void init() {
		closed = false;
		found = false;
		imageBuffer = new LinkedList<BufferedImage>();
		
		setProperties();
		
		createScanImage();
		
		refresh();
		
		startCapture();
	}
	
	private void setProperties() {
	
		// Set Look and Feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		
		// Set Other Properties
		setVisible(true);
		setSize(800, 600);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("img/icon.png").getImage());
		
		addWindowListener(this);
	}
	
	private void createScanImage() {
		if (scanPanel == null) scanPanel = new ScanPanel(main);
		add(scanPanel);
	}
	
	private void refresh() {
		revalidate();
		repaint();
	}
	
	private void startCapture() {
			new Thread(new QRCapture(main)).start();
			new Thread(new QRDetect(main)).start();
	}
	
	public void displayCapture(BufferedImage img) {
		scanPanel.setImg(img);
	}
	
	public BufferedImage pollBuffer() {
		return imageBuffer.poll();
	}
	
	public void enqueueBuffer(BufferedImage img) {
		imageBuffer.add(img);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		closed = true;
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

}
