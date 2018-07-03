package piano_package;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class PianoTester {
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setLayout(manager);
		FullPiano p = new FullPiano();
		f.add(p, BorderLayout.CENTER);
		
		f.setSize(500, 400);
		f.setVisible(true);
	}
}
