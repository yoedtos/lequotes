package net.yoedtos.lequotes.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import net.yoedtos.lequotes.entity.DayQuote;
import net.yoedtos.lequotes.exception.AppException;
import net.yoedtos.lequotes.persistence.DAOException;
import net.yoedtos.lequotes.utils.FileUtils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayQuoteService extends Service {
	
	private static final Logger LOOGER = LoggerFactory.getLogger(DayQuoteService.class);
	private String quoteTxt;
	
	public String load() throws AppException {
		dayQuote = getDayQuote();
		
		try {
			lastState = FileUtils.loadState();
			dayQuote.setId(lastState.getLastid());
		} catch (ClassNotFoundException | IOException e) {
			LOOGER.error(e.getMessage());
			dayQuote.setId(1);
		}
		
		try {			
			dayQuoteDao.load(dayQuote);
			quoteTxt = getTxtQuotes(dayQuote);
		} catch (DAOException | IOException e1) {
			LOOGER.error(e1.getMessage());
			throw new AppException("Failed to get Quotes");
		}
		
		LOOGER.info("ID = " + dayQuote.getId());
		return quoteTxt;	
	}
	
	public String previous() throws AppException {
		dayQuote = getDayQuote();
		id = dayQuote.getId();
		id--;
		dayQuote.setId(id);
	
		try {
			dayQuoteDao.load(dayQuote);
			quoteTxt = getTxtQuotes(dayQuote);
		} catch (DAOException | IOException e) {
			LOOGER.error(e.getMessage());
			throw new AppException("Failed to get Quotes");
		}
		
		LOOGER.info("ID = " + id);
		
		return quoteTxt; 
	}
	
	public String next() throws AppException {
		dayQuote = getDayQuote();
		id = dayQuote.getId();
		id++;
		dayQuote.setId(id);
		
		try {
			dayQuoteDao.load(dayQuote);
			quoteTxt = getTxtQuotes(dayQuote);
		} catch (DAOException | IOException e) {
			LOOGER.error(e.getMessage());
			throw new AppException("Failed to get Quotes");
		}
		
		LOOGER.info("ID = " + id);
		return quoteTxt;
	}
	
	public void shutdown() throws AppException {
		dayQuote = getDayQuote();
		lastState.setLastid(dayQuote.getId());
		try {
			FileUtils.saveState(lastState);
			dayQuoteDao.closeConnection();
		} catch (DAOException | IOException e) {
			LOOGER.error(e.getMessage());
			throw new AppException("Shutdown with failure");
		}
		
		LOOGER.info("Shutdown Success");
	}
	
	private String getTxtQuotes(DayQuote dayQuote) throws IOException {
		
		byte[] quoteByte = IOUtils.toByteArray(dayQuote.getTxtReader(), StandardCharsets.UTF_8);
		
		return new String(quoteByte, StandardCharsets.UTF_8);
	}
	
	private DayQuote getDayQuote() {
		return SingleDayQuote.getInstance();
	}
}
