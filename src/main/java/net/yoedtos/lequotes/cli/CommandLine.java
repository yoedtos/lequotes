package net.yoedtos.lequotes.cli;

import java.io.File;
import java.io.IOException;

import net.yoedtos.lequotes.entity.DayQuote;
import net.yoedtos.lequotes.entity.FileDayQuote;
import net.yoedtos.lequotes.persistence.DAOException;
import net.yoedtos.lequotes.persistence.DAOUtility;
import net.yoedtos.lequotes.persistence.DayQuoteDAO;
import net.yoedtos.lequotes.utils.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLine {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandLine.class);
	
	private static final String HELP = 
			"-------------LeQuotes Command Line-----------------\n" +
			" h                          --> show this help\n" +
			" s [diretory]               --> scan to add records\n" +
			" a [audio file] [txt file]  --> add one record\n" +
			"\n" +
			"-------------Advanced Command Line-----------------\n" + 
			" R [backup dir]             --> repair database\n";
	
	private static final String INVALID = "Is not a valid directory!";
	
	private String[] commands;
	
	public CommandLine(String[] args) {
		this.commands = args;
		
		String command = commands[0];
		
		if(command.equals("h")) {
			System.out.println(HELP);
		} else if(command.equals("s")) {
			if(commands.length == 2) {
				File path = new File(commands[1]);
				if(path.exists() && path.canRead()) {
					Scanner scanner = new Scanner();
					scanner.scan(path);
				} else {
					System.out.println(INVALID);
				}
			} else {
				System.out.println(INVALID);
			}
		} else if(command.equals("a")) {
			
			FileDayQuote fileDayQuote = new FileDayQuote();
			DayQuoteDAO quoteDao = new DayQuoteDAO();
			
			fileDayQuote.setFileAudio(new File(commands[1]));
			fileDayQuote.setFileText(new File(commands[2]));
			
			DayQuote quote;
			try {
				quote = FileUtils.populetDayQuote(fileDayQuote, new DayQuote());
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
			
			int returnCode = 0;
			Integer id = null;
			
			try {
				id = quoteDao.getNullRow();
			} catch (DAOException e) {
				LOGGER.error(e.getMessage());
				System.out.println("Process failed!");
			}
			
			if(id != null) {
				try {
					returnCode = quoteDao.update(quote, id);
					System.out.println("Record " + id + " updated " + "return code " + returnCode);
				} catch (DAOException e) {
					LOGGER.error(e.getMessage());
					System.out.println("Failed to update record: " + id + " return code " + returnCode);
				}
				
			} 
			else {
				try {
					returnCode = quoteDao.add(quote);
					System.out.println("Inserted one record " + "return code " + returnCode);
				} catch (DAOException e) {
					LOGGER.error(e.getMessage());
					System.out.println("Failed to insert record: " + "return code " + returnCode);
				}
				
			}
		} else if(command.equals("R")) {
			String folder;
			
			if(commands.length == 2) {
				folder = commands[1];
			} else {
				folder = "dbBackup";
			}
			
			File directory = new File(folder);
			if(!directory.exists()) {
				if(directory.mkdir()) {
					LOGGER.debug("Directory created!");
				}
			}
			LOGGER.debug("Backup to " + folder);
			
			DAOUtility.recoverDB(directory);
			
		} else {
			System.out.println(commands[0] + " Not be a valid command!");
			System.out.println("Please enter h to get help");
		}
	}
}
