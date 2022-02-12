package net.yoedtos.lequotes.service;

import java.io.ByteArrayOutputStream;

import net.yoedtos.lequotes.exception.AudioException;


public class AudioService extends Service {
	
	
	public void setVolume(float value) {
		float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
		player.setGain(dB);
	}
	
	public Object[] initCapture() throws AudioException {
		return player.initCapture();
	}
	
	public void dropCapture(ByteArrayOutputStream captured) {
		dayQuote = SingleDayQuote.getInstance();
		dayQuote.setCaptured(captured);
		player.dropCapture();
	}
	
	public Object[] initPlay() throws AudioException {
		dayQuote = SingleDayQuote.getInstance();
		
		if(dayQuote.getCaptured() == null) {
			return player.initPlay(dayQuote.getAudioByte());
		} else {
			return player.initPlay(dayQuote.getCaptured());
		}		
	}
	
	public void dropPlay() {
		player.dropPlay();
	}
	
	public boolean hasCaptured() {
		return dayQuote.getCaptured() == null ? false : true;
	}
	
	public void clear(){
		dayQuote = SingleDayQuote.getInstance();
		dayQuote.setCaptured(null);
	}

}
