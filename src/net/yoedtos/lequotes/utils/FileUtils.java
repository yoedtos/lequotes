package net.yoedtos.lequotes.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.yoedtos.lequotes.entity.DayQuote;
import net.yoedtos.lequotes.entity.FileDayQuote;

public class FileUtils {

	private FileUtils() {

	}

	private static String lastState = "laststate.bin";
	
	public static DayQuote populetDayQuote(FileDayQuote fileDayQuote, DayQuote dayQuote) throws IOException {
	
		dayQuote.setTitle(FileUtils.getTitleFromTxt(fileDayQuote.getFileText()));
		dayQuote.setTxtReader(new FileReader(fileDayQuote.getFileText()));
		dayQuote.setAudio(new FileInputStream(fileDayQuote.getFileAudio()));
		 
		return dayQuote;
	}
	
	public static void saveState(LastState state) throws IOException {
		
		try (ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(lastState))){
			objOutput.writeObject(state);
		} 
	}
	
	public static LastState loadState() throws IOException, ClassNotFoundException {
		
		LastState state = null;

		try (ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(lastState))) {
			state = (LastState)objInput.readObject();
		} 
		
		return state;
	}
	
	public static String getTitleFromTxt(File text) throws IOException {
		
		String title = null;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(text))) {
			while ((title = reader.readLine()) != null) {
				
				if (title.length() != 0) {
					title = title.trim();
					break;
				}
			}
		}
		
		return title;
	}
}
