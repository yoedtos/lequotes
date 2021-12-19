package net.yoedtos.lequotes.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.yoedtos.lequotes.exception.AppException;
import net.yoedtos.lequotes.service.DayQuoteService;
import net.yoedtos.lequotes.ui.UiMessage;

public class QuitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		DayQuoteService service = new DayQuoteService();
		try {
			service.shutdown();
		} catch (AppException ex) {
			UiMessage.showMessage(ex.getMessage(), "LeQuotes - Error!", true);
			System.exit(1);
		}
		System.exit(0);
	}

}
