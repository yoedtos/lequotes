package net.yoedtos.lequotes.service;

import net.yoedtos.lequotes.entity.DayQuote;

public class SingleDayQuote {

	private static DayQuote dayQuote;
	
	private SingleDayQuote() {
		
	}
	
	public static DayQuote getInstance() {
		if(dayQuote == null) {
			dayQuote = new DayQuote();
		}
		return dayQuote;
	}
	
	public DayQuote getDayQuote() {
		return dayQuote;
	}

	public void setDayQuote(DayQuote dayQuote) {
		SingleDayQuote.dayQuote = dayQuote;
	}

}
