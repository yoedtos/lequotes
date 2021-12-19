package net.yoedtos.lequotes.utils;


public class AudioUtils {
	
	private AudioUtils() {
	}

	public static float calculateLevel(byte[] audio) {
		
		float level;
		//This calculate method only work in 16 bits sampled PCM
		if(audio != null) {
			float max = 0;
			float MAX_16_BITS_SIGNED = Short.MAX_VALUE;
			
			for (int i = 0; i < audio.length; i += 2) {
				int value = 0;
				int hiByte = audio[i + 1];
				int loByte = audio[i];
				
				short shortVal = (short) hiByte;
				shortVal = (short) ((shortVal << 8) | (byte) loByte);
				value = shortVal;
				
				max = Math.max(max, value);
			}
			
			level = max / MAX_16_BITS_SIGNED;

		} else {
			level = 0;
		}
		return level;
	}
}
