package net.yoedtos.lequotes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Status extends JComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(Status.class);
	
	private static final long serialVersionUID = 1L;
	
	private float level;
	private long time;
	private int baseWidth = 100;
	private int barHeight = 20;
	private Font displayFont;

	public Status() {
		this.setPreferredSize(new Dimension(100, 55));
		this.level = 0;
		this.time = 0;
		displayFont = loadCustomFont();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D status = (Graphics2D) g;
		
		Rectangle2D frame = new Rectangle2D.Double(5, 0, 210, 52);
		status.draw(frame);
		
		status.setColor(Color.BLACK);
		status.drawRect(13, 20, 103, 22);
		status.drawString("Level", 50, 13);
		
		double barWidth = baseWidth * level;
		Rectangle2D green = new Rectangle2D.Double(15, 21, barWidth, barHeight);
		status.setColor(Color.GREEN);
		status.draw(green);
		status.fill(green);
		
		if(barWidth > 75) {
			Rectangle2D red = new Rectangle2D.Double(90, 21, barWidth -75, barHeight);
			status.setColor(Color.RED);
			status.draw(red);
			status.fill(red);
		}

		status.setColor(Color.BLACK);
		status.drawRect(135, 16, 72, 30);
		status.drawString("Time", 157, 13);
		status.setFont(displayFont);
		status.drawString(String.format("%02d:%02d", (time % 3600)/60, (time % 60)), 140, 40);
	}

	public void setFill(float level, long time) {
		this.level = level;
		this.time = time;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});
	}
	
	private Font loadCustomFont() {
		Font display = null;
		InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("font/FreeSerif.ttf");
		try {
			display = Font.createFont(Font.TRUETYPE_FONT, resourceStream);
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			environment.registerFont(display);
			display = display.deriveFont(26f).deriveFont(Font.BOLD);
		} catch (IOException | FontFormatException e) {
			LOGGER.error("Cannot load font! Using default");
			display = new Font("Serif", Font.BOLD, 22);
		} 
		return display;
	}
}
