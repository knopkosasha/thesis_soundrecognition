package ru.amaslakova.soundrecognition.fingerprint.hash.peak;


import ru.amaslakova.soundrecognition.fingerprint.util.Hash;

/**
 * Represents a two peaks in a given audio file
 * with the ability to hash the two peaks
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public final class HashedPeak {
	
	private final Peak one;
	private final Peak two;
	private final int delta;
	
	public HashedPeak(Peak one, Peak two, int delta) {
		this.one = one;
		this.two = two;
		this.delta = delta;
	}
	
	public Peak getPeakOne() {
		return this.one;
	}
	
	public Peak getPeakTwo() {
		return this.two;
	}
	
	public int getDelta() {
		return this.delta;
	}
	
	public byte[] getHash() {
		return Hash.calculate(this.one.getFreq() + "|" + this.two.getFreq() + "|" + this.delta);
	}
	
	public String getHashAsHex() {
		return Hash.toHex(getHash());
	}
	
}
