package ru.amaslakova.soundrecognition.fingerprint;



/**
 * Parses the header information from the audio file buffer
 * 
 * Information regarding the header file format can be found here
 * http://soundfile.sapp.org/doc/WaveFormat/
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public class AudioFileHeader {

	private final String chunkId;	// 4 bytes
	private final int chunkSize; // unsigned 4 bytes, little endian
	private final String format;	// 4 bytes
	private final String subChunkOneId;	// 4 bytes
	private final int subChunkOneSize; // unsigned 4 bytes, little endian
	private final int audioFormat; // unsigned 2 bytes, little endian
	private final int channels; // unsigned 2 bytes, little endian
	private final int sampleRate; // unsigned 4 bytes, little endian
	private final int byteRate; // unsigned 4 bytes, little endian
	private final int blockAlign; // unsigned 2 bytes, little endian
	private final int bitsPerSample; // unsigned 2 bytes, little endian
	private final String subChunkTwoId;	// 4 bytes
	private final int subChunkTwoSize; // unsigned 4 bytes, little endian

	public AudioFileHeader(AudioFileBuffer buffer) {
		this.chunkId = buffer.getString();
		this.chunkSize = buffer.getInt();
		this.format = buffer.getString();
		this.subChunkOneId = buffer.getString();
		this.subChunkOneSize = buffer.getInt();
		this.audioFormat = buffer.getShort();
		this.channels = buffer.getShort();
		this.sampleRate = buffer.getInt();
		this.byteRate = buffer.getInt();
		this.blockAlign = buffer.getShort();
		this.bitsPerSample = buffer.getShort();
		this.subChunkTwoId = buffer.getString();
		this.subChunkTwoSize = buffer.getInt();
	}
	
	public String getChunkId() {
		return this.chunkId;
	}

	public int getChunkSize() {
		return this.chunkSize;
	}

	public String getFormat() {
		return this.format;
	}

	public String getSubChunkOneId() {
		return this.subChunkOneId;
	}

	public int getSubChunkOneSize() {
		return this.subChunkOneSize;
	}

	public int getAudioFormat() {
		return this.audioFormat;
	}

	public int getChannels() {
		return this.channels;
	}

	public int getSampleRate() {
		return this.sampleRate;
	}

	public int getByteRate() {
		return this.byteRate;
	}

	public int getBlockAlign() {
		return this.blockAlign;
	}

	public int getBitsPerSample() {
		return this.bitsPerSample;
	}

	public String getSubChunkTwoId() {
		return this.subChunkTwoId;
	}

	public int getSubChunkTwoSize() {
		return this.subChunkTwoSize;
	}

}
