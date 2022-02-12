package net.yoedtos.lequotes.ui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.yoedtos.lequotes.ui.action.AboutAction;
import net.yoedtos.lequotes.ui.action.QuitAction;

public class Menu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public Menu() {
		
		Dimension size = new Dimension(70, 30);
		
		JMenu system = new JMenu("System");
		system.setMnemonic(KeyEvent.VK_S);
		system.setPreferredSize(size);
		
		JMenuItem quit = new JMenuItem(new QuitAction());
		quit.setIcon(ImagesB.EXIT);
		quit.setText("Quit");
		quit.setMnemonic(KeyEvent.VK_Q);
		system.add(quit);
		add(system);
		
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		help.setPreferredSize(size);
		
		JMenuItem about = new JMenuItem(new AboutAction());
		about.setIcon(ImagesB.ABOUT);
		about.setText("About");
		about.setMnemonic('A');
		help.add(about);
		add(help);
	}
}
