package piano_package;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Keys {
	private int xPos;
	private int yPos=0;
	private int xSize;
	private int ySize;
	private char note;
	private char sharp_flat; //I just use sharp but both are implemented 
		//I don't account for white keys as sharps/flats
	private int key; //if black: 1-5	if white: 1-7
	private int keyFreq; //key for frequency
	private Rectangle2D.Double outline;
	private boolean isBlackKey;
	
	
	public Keys(char note, char sharp_flat){
		this.note = note;
		this.sharp_flat = sharp_flat;
	}
	
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		//need to set 'key' and xPos (x-coordinates) and xSize, ySize etc
		
		//position based on note
		int ascii = note;
//		//normalize into key
		int preliminaryKey;
//		//notes C, D, E, F, G
		if (ascii>=67) {
			preliminaryKey = ascii - 67;
		}
		else { //A, B
			preliminaryKey = ascii - 60;
		}
		
		//black or white key
		if (sharp_flat == '\u0000') { //white key
			isBlackKey = false;
			ySize = 321;
			xSize = 64;
			
			key = preliminaryKey;	
			
			xPos = key*64; //assign x-coordinate
		}
		else { //black key
			isBlackKey = true;
			ySize = 193;
			xSize = 43;
			
			//sharp or flat
			if (sharp_flat == 's') {//sharp
				if (note== 'C' || note== 'D') {
					key = preliminaryKey;
				}
				else {
					key = preliminaryKey -1;	
				}
			}
			else {//would == 'f'
				if (note=='D' || note=='E') {
					key = preliminaryKey -1;
				}
				else {
					key = preliminaryKey -2;
				}
			}
			
			//assign x-coordinate
			if (key>=2){
				xPos = 48 + 64*key + 64;
			}
			else {
				xPos = 48 + 64*key;
			}
		}
		
		
		//everything is set, now draw
		outline = new Rectangle2D.Double(xPos, yPos, xSize, ySize);
		g2.setColor(Color.BLACK);
		if (sharp_flat != '\u0000') { //black key
			g2.fill(outline); 
		}
		g2.draw(outline);
		
		
		
		//calls method to set frequency [I put here because everything needed to set freq is determined - tho not part of draw]
		setKeyFreq();
	}

	//for contains method that check clicks
	public Rectangle2D.Double getOutline() {
		return outline;
	}
	
	public boolean getIsBlackKey() {
		return this.isBlackKey;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public char getNote() {
		return this.note;
	}
	
	public char getSharp_Flat() {
		return this.sharp_flat;
	}
	
	public int getKeyFreq() {
		return this.keyFreq;
	}
	
	public void setKeyFreq() { //merge two separate lists for black and white into one for sound algorithm
		if(!isBlackKey) { //white
			if (key<=2) {
				keyFreq = key*2;
			}
			else {
				keyFreq = key*2 -1;
			}
		}
		else {
			if(key<=1) {
				keyFreq = key*2 +1;
			}
			else {
				keyFreq = key*2 +2;
			}
		}
	}
}

