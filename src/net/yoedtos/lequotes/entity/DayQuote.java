package net.yoedtos.lequotes.entity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Reader;

public class DayQuote {
	
	private int id;
	private String title;
	private Reader txtReader;
	private InputStream audio;
	private ByteArrayOutputStream captured;
	private byte[] audioByte;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Reader getTxtReader() {
		return txtReader;
	}
	
	public void setTxtReader(Reader txtReader) {
		this.txtReader = txtReader;
	}
	
	public InputStream getAudio() {
		return audio;
	}

	public void setAudio(InputStream audio) {
		this.audio = audio;
	}
	
	public ByteArrayOutputStream getCaptured() {
		return captured;
	}

	public void setCaptured(ByteArrayOutputStream captured) {
		this.captured = captured;
	}

	public byte[] getAudioByte() {
		return audioByte;
	}

	public void setAudioByte(byte[] audioByte) {
		this.audioByte = audioByte;
	}
}
