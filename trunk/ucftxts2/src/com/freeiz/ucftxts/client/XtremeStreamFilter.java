
package com.freeiz.ucftxts.client;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;


/**
 * @author Michael Scherer
 *	This is to compensate for the free web server we have which adds info after the </xml> tag.
 *	NOTE: Assumes UTF-8 encoding
 */
public class XtremeStreamFilter extends FilterInputStream
{
	private final String mEnd = "</xml>";
	private byte[] mEndBytes;
	private int mPos=0;
	private boolean mDone=false;
	
	private final char mEndOfTransmission = '\u0004';
	
	/**
	 * Creates the filter
	 * @param in the input stream
	 */
	protected XtremeStreamFilter(InputStream in)
	{
		super(in);
		// TODO Auto-generated constructor stub
		mPos = 0;
		mEndBytes = EncodingUtils.getBytes(mEnd, "UTF-8");
		mDone = false;
	}
	
	/**
	 * Reads in a single int
	 * @return the read in int, or -1 if the stream ended
	 */
	@Override
	public int read() throws IOException
	{
		if (mDone)
			return -1;
		
		// TODO Auto-generated method stub
		int val = super.read();
		if (val == mEndBytes[mPos])
			mPos++;
		else
			mPos = 0;
		
		if (mPos >= mEndBytes.length)
			mDone = true;
		
		return val;
	}
	
	/**
	 * Reads in an array of bytes
	 * @param buffer the data to be read in
	 * @param offset the starting point in the buffer
	 * @param count the number of elements to read in the buffer
	 * @return the number of bytes read, or -1 if none
	 */
	@Override
	public int read(byte[] buffer, int offset, int count) throws IOException
	{
		boolean done=false;
		int tmp=0;
		int bytesread=0;
		for (int i=offset; i<offset+count; i++)
		{
			if (done || -1 == (tmp = read()))
			{
				done=true;
				buffer[i] = 0;
				if (i==offset)
					return -1;
			}
			else
			{
				buffer[i] = (byte)tmp;
				bytesread++;
			}
		}
		return bytesread;
	}
}
