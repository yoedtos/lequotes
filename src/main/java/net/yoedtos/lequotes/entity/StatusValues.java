package net.yoedtos.lequotes.entity;

public class StatusValues {

	private long time;
	private float level;
	
	public StatusValues(float level, long time) {
		this.level = level;
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}
	
	public float getLevel() {
		return level;
	}
}
