package net.yoedtos.lequotes.audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.lequotes.exception.AudioException;

public class AudioPlayer {

	private static final Logger LOGGER = LoggerFactory.getLogger(AudioPlayer.class);
	
	private SourceDataLine sourceLine;
	private TargetDataLine targetLine;
	
	public void setVolume(float dB) {
		if(sourceLine != null) {
			FloatControl gainControl = (FloatControl) sourceLine.getControl(FloatControl.Type.VOLUME);
			float maximum = gainControl.getMaximum();
			float minimun = gainControl.getMinimum();
			LOGGER.debug("Gain Range: " + minimun + " - " + maximum);
			float current = gainControl.getValue();
			gainControl.setValue(dB);
			LOGGER.debug("Current Volume: " + current + " changed to " + gainControl.getValue());
		}
	}
	
	public void setGain(float dB) {
		if(sourceLine != null) {
			FloatControl gainControl = (FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN);
			float maximum = gainControl.getMaximum();
			float minimun = gainControl.getMinimum();
			LOGGER.debug("Gain Range: " + minimun + " - " + maximum);
			float current = gainControl.getValue();
			gainControl.setValue(dB);
			LOGGER.debug("Current Gain: " + current + " changed to " + gainControl.getValue());
		}
	} 
	
	public Object[] initCapture() throws AudioException {
	
		try {
			AudioFormat audioFormat = createAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			targetLine.open(audioFormat);
			targetLine.start();
		} catch (LineUnavailableException e) {
			LOGGER.error(e.getMessage());
			throw new AudioException("The line is not available");
		}
		
		Object[] objects = new Object[2];
		objects[0] = targetLine;
		objects[1] = new ByteArrayOutputStream();

		return objects;
	}

	public void dropCapture() {
		targetLine.stop();
		targetLine.close();			
	}
	
	public Object[] initPlay(byte[] audio) throws AudioException {
		
    	ByteArrayInputStream bai = new ByteArrayInputStream(audio);
    	
    	AudioInputStream input = null;
		try {
			input = AudioSystem.getAudioInputStream(bai);
		} catch (UnsupportedAudioFileException e) {
			LOGGER.error(e.getMessage());
			throw new AudioException("Audio file is not supported");
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new AudioException("Can't read file");
		}
        
        AudioFormat audioFormat = getAudioFormat(input.getFormat());
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFormat, input);
        
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			LOGGER.error(e.getMessage());
			throw new AudioException("The line is not available");
		}

            if (sourceLine != null) {
            	try {
					sourceLine.open(audioFormat);
				} catch (LineUnavailableException e) {
					LOGGER.error(e.getMessage());
					throw new AudioException("The line is not available");
				}
                sourceLine.start();
            }        

            Object[] objects = new Object[2];
            objects[0] = sourceLine;
            objects[1] = audioInputStream;
            
            return objects;
	}
	
	public void dropPlay() {
     	sourceLine.stop();
		sourceLine.close();
 
	}
	
	
	public Object[] initPlay(ByteArrayOutputStream captured) throws AudioException {
		
		byte[] audioData = captured.toByteArray();
		
		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
		
		AudioFormat audioFormat = createAudioFormat();
		int audioLength = audioData.length/audioFormat.getFrameSize();
		AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, audioLength);
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
				
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		} catch (LineUnavailableException e) {
			LOGGER.error(e.getMessage());
			throw new AudioException("The line is not available");
		}
		
       if (sourceLine != null) {
			try {
				sourceLine.open(audioFormat);
			} catch (LineUnavailableException e) {
				LOGGER.error(e.getMessage());
				throw new AudioException("The line is not available");
			}
            sourceLine.start();
       }
           
        Object[] objects = new Object[2];
        objects[0] = sourceLine;
        objects[1] = audioInputStream;
        
        return objects;
	}
	    
    private AudioFormat getAudioFormat(AudioFormat inFormat) {
        
    	int channels = inFormat.getChannels();
        float sampleRate = inFormat.getSampleRate();
        
        return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16, channels, channels * 2, sampleRate, false);
    }

	private AudioFormat createAudioFormat() {
		
		float sampleRate = 8000.0F; 
		int sampleSizeInBits = 16;  
		int channels = 1;           
		boolean signed = true;     
		boolean bigEndian = false;
		
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
}
