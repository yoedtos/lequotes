package net.yoedtos.lequotes.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import net.yoedtos.lequotes.exception.AppException;

public class GuiMain extends JFrame implements WindowListener {
 
	private static final long serialVersionUID = 1L;
	
    private ControlPanel btnPanel;
  
    public GuiMain() {
    	super("LeQuotes - Learn English by Quotes");
    	
    	setDefaultLookAndFeelDecorated(true);
    	setLayout(new BorderLayout());
    	
    	Menu menuBar = new Menu();
    	TextPanel txtPanel = new TextPanel();
    	btnPanel = new ControlPanel(txtPanel.getTxtPanel());
    	
        add(btnPanel, BorderLayout.NORTH);
        add(txtPanel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        setJMenuBar(menuBar);
        
        addWindowListener(this);
        
        setResizable(false);
        setVisible(true);
    }

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			btnPanel.getDayQuoteService().shutdown();
		} catch (AppException ex) {
			UiMessage.showMessage(ex.getMessage(), "LeQuotes - Error!", true);
			System.exit(1);
		}
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
	     
}
