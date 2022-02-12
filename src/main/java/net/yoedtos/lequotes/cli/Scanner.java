package net.yoedtos.lequotes.cli;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.lequotes.entity.DayQuote;
import net.yoedtos.lequotes.entity.FileDayQuote;
import net.yoedtos.lequotes.persistence.DAOException;
import net.yoedtos.lequotes.persistence.DayQuoteDAO;
import net.yoedtos.lequotes.utils.FileUtils;

public class Scanner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Scanner.class);
	
	private Set<String> audioSet;
	private Set<String> txtSet;
	private FileScanner txtFileScan;
	private FileDayQuote fileDayQuote;
	private DayQuote dayQuote;
	private DayQuoteDAO dayQuoteDAO;
	private int counter;
	private int errors;
	private int[] savedItems;
	

	public void scan(File path) {
		
		txtFileScan = new FileScanner();
		fileDayQuote = new FileDayQuote();
		dayQuote = new DayQuote();
		dayQuoteDAO = new DayQuoteDAO();
		
		audioSet = txtFileScan.getAudioFileList(path);
		txtSet = txtFileScan.getTxtFileList(path);
		
		for (String txtname : txtSet) {
			
			fileDayQuote.setFileText(new File(txtname));
			try {
				dayQuote.setTitle(FileUtils.getTitleFromTxt(fileDayQuote.getFileText()));
				System.out.println("- " + fileDayQuote.getFileText().getName());
			} catch (IOException e) {
				LOGGER.debug(e.getMessage());
				errors++;
				System.out.println("Error: Cannot read title! " + fileDayQuote.getFileText().getName());
			}
			
			for(String audioFile : audioSet) {
				int i = audioFile.lastIndexOf(".");
				String audioname = audioFile.substring(0, i);
				if(txtname.equals(audioname)) {
					fileDayQuote.setFileAudio(new File(audioFile));
					System.out.println("- " + fileDayQuote.getFileAudio().getName());
					break;
				}
			}
			
			try {
				dayQuoteDAO.addByLote(FileUtils.populetDayQuote(fileDayQuote, dayQuote));
			} catch (DAOException | IOException e) {
				LOGGER.debug(e.getMessage());
				throw new RuntimeException("Terminated: Add process failed!");
			}
		}
		
		System.out.println("Total Scanned Files: ");
		System.out.println("Scan errors: " + errors);
		System.out.println("Text files: " + txtSet.size());
		System.out.println("Audio files: " + audioSet.size());
		
		try {
			savedItems = dayQuoteDAO.saveLote();
		} catch (DAOException e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException("Terminated: Save process failed!");
		}
	    
		for (int i= 0; i< savedItems.length; i++) {	
			counter = savedItems[i] + counter;
	    }

		System.out.println("Added " + counter + " records in database.");
		
	}
}
