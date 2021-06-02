package ru.amaslakova.soundrecognition.fingerprint;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Reads the audio file into a buffer and stores it for.
 * further use.
 * 
 * @author Derek Honerlaw <honerlawd@gmail.com>
 */
public class AudioFileBuffer {
	
	/**
	 * The file's content
	 */
	private final byte[] buffer;
	
	/**
	 * The current position in the buffer
	 */
	private int position;

	/**
	 * Read the content from the given wav file
	 * 
	 * @param file The file to read from
	 * @throws IOException
	 */
	public AudioFileBuffer(AudioFile file) throws IOException {
		InputStream stream = new FileInputStream(file.getWAVFilePath());
		this.buffer = new byte[stream.available()];
		stream.read(this.buffer);
		stream.close();
	}
	
	/**
	 * Get a byte at a specific position
	 * 
	 * @param position The position to read from
	 * @return
	 */
	public byte get(int position) {
		return this.buffer[position];
	}
	
	/**
	 * Read a short from the buffer
	 * @return The read short
	 */
	public int getShort() {
		return (int) ((this.buffer[this.position++] & 0xff) | (this.buffer[this.position++] & 0xff) << 8);
	}
	
	/**
	 * Read a integer from the buffer
	 * @return The read integer
	 */
	public int getInt() {
		 return (int) (this.buffer[this.position++] & 0xff)
			| (int) (this.buffer[this.position++] & 0xff) << 8
			| (int) (this.buffer[this.position++] & 0xff) << 16
			| (int) (this.buffer[this.position++] & 0xff) << 24;
	}
	
	/**
	 * Read a 4 byte string from the buffer
	 * @return The read string
	 */
	public String getString() {
		return new String(new byte[] {
				this.buffer[this.position++],
				this.buffer[this.position++],
				this.buffer[this.position++],
				this.buffer[this.position++]
		});
	}
	
	/**
	 * Get the size of the buffer
	 * @return
	 */
	public int size() {
		return this.buffer.length;
	}

}
