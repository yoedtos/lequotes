package net.yoedtos.lequotes.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.yoedtos.lequotes.entity.About;
import net.yoedtos.lequotes.ui.AboutGUI;

public class AboutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		String credits = "English content provided by\n" +
				" - EFR - English for Reading\n" +
				"\n" +
				"This software uses:\n" +
				" - H2 Database\n" +
				" - JavaZoom (JLayer, mp3spi)\n" +
				" - Tritonus: Open Source Java Sound\n" +
				" - Apache Commons";
		
		About about = new About();
		about.setTitle("Learning English by Quotes");
		about.setName("LeQuotes");
		about.setVersion("1.0.0");
		about.setAuthor("Edson Yoshimaru");
		about.setCredits(credits);
		
		AboutGUI aboutGUI = new AboutGUI(about);
		aboutGUI.setVisible(true);
	}

}
