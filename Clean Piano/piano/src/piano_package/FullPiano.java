package piano_package;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import javax.sound.sampled.*;


public class FullPiano extends JPanel{
	public static float SAMPLE_RATE = 8000f;
	
	private ArrayList<Keys> whiteKeys;
	private ArrayList<Keys> blackKeys;

	public FullPiano() {
		whiteKeys = new ArrayList<>();
		blackKeys = new ArrayList<>();
		repaint();
		addMouseListener(new Listener()); //for Listener and Mouse stuff to work!!!!
	}
	
	
	//where keys are made and assigned to whiteKeys or blackKeys
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 65; i<=71; i++) {
			Keys w = new Keys((char)i, '\u0000');
			whiteKeys.add(w);
			w.draw(g);
			
			//black keys
			if (!(i==69 || i==66)) {
				Keys b = new Keys((char)i, 's');
				blackKeys.add(b);
				b.draw(g);
			}
		}
	}
	
	
	//when user clicks, checks which key, then calls static method 'tone' to make the sound
	private class Listener extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			Point mousePoint = event.getPoint();
			Keys tempKey = null;
			
			//check contains for sharps and flats before white keys
			for(Keys b : blackKeys) {
				Rectangle2D.Double tempOut = b.getOutline();
				if(tempOut.contains(mousePoint)) {
					tempKey = b;
					break;
				}
			}
			
			//then check white keys
			if(tempKey == null) {
				for(Keys w : whiteKeys) {
					Rectangle2D.Double tempOut = w.getOutline();
					if(tempOut.contains(mousePoint)) {
						tempKey = w;
						break;
					}
				}
			}
			
			if(tempKey == null) {
				System.out.println("Please choose a key.");
				return;
			}
			
			//sound part!!
			int freq = tempKey.getKeyFreq() + 40;
			double temp = Math.pow(2, ((freq-49)/12.0));
			temp = temp*440;
			freq = (int) temp;
			System.out.println(freq);
			try {
				tone(freq, 500);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	//method that takes in freq. and produces sound
	public static void tone(int hz, int msecs) throws LineUnavailableException { 
			byte[] buf = new byte[1];
			AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
			SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
			sdl.open(af);
			sdl.start();
			for (int i = 0; i<msecs*8; i++) {
				double angle = i / (SAMPLE_RATE / hz)*2.0*Math.PI;
				buf[0] = (byte)(Math.sin(angle)*127.0*(1.0));
				sdl.write(buf, 0, 1);
			}
			sdl.drain();
			sdl.stop();
			sdl.close();
	}
}
