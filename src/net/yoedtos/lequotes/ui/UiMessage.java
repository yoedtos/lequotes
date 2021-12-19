package net.yoedtos.lequotes.ui;

import javax.swing.JOptionPane;

public class UiMessage extends JOptionPane {

	private static final long serialVersionUID = 1L;

	public static void showMessage(String message, String title, boolean isError) {
		
		int type = JOptionPane.INFORMATION_MESSAGE;
		
		if(isError) {
			type = JOptionPane.ERROR_MESSAGE;
		}
		
		showMessageDialog(null, message, title, type);
	}
}
