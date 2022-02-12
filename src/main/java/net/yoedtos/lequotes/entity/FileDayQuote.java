package net.yoedtos.lequotes.entity;

import java.io.File;

public class FileDayQuote {

	private File fileText;
	private File fileAudio;
	
	public File getFileText() {
		return fileText;
	}
	
	public File getFileAudio() {
		return fileAudio;
	}
	
	public void setFileText(File fileText) {
		this.fileText = fileText;
	}
	
	public void setFileAudio(File fileAudio) {
		this.fileAudio = fileAudio;
	}
}
