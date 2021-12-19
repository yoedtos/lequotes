package net.yoedtos.lequotes.service;

import net.yoedtos.lequotes.audio.AudioPlayer;
import net.yoedtos.lequotes.entity.DayQuote;
import net.yoedtos.lequotes.persistence.DayQuoteDAO;
import net.yoedtos.lequotes.utils.LastState;

public abstract class Service {

	protected int id = 0;
	protected DayQuote dayQuote;
	protected DayQuoteDAO dayQuoteDao = new DayQuoteDAO();
	protected AudioPlayer player = new AudioPlayer();
	protected LastState lastState = new LastState();

}
