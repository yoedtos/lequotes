package net.yoedtos.lequotes;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.yoedtos.lequotes.cli.CommandLine;
import net.yoedtos.lequotes.ui.GuiMain;
import net.yoedtos.lequotes.ui.UiMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws Exception {
		
		if(args.length == 0) {
			LOGGER.info("Starting GUI...");
			
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				UiMessage.showMessage("Unknow error occour!", "LeQuotes - Terminated", true);
			} 
			
			SwingUtilities.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						new GuiMain();
					}
			});
			
		} else {
			System.out.println("Starting Command Line Interface...");
			new CommandLine(args);
		}
	}
}
