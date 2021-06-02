package ru.amaslakova.soundrecognition.fingerprint.hash.peak;

/**
 * Represents a peak with a given time and frequency
 * for a specific audio file
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public final class Peak {
	
	private final int time;
	private final int freq;
	
	public Peak(int time, int freq) {
		this.time = time;
		this.freq = freq;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public int getFreq() {
		return this.freq;
	}
	
}
