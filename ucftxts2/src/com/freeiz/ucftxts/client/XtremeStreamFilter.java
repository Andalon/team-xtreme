/**
 * 
 */
package com.freeiz.ucftxts.client;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;


/**
 * @author michael
 *
 */
public class XtremeStreamFilter extends FilterInputStream
{
	private final String mEnd = "</xml>";
	private byte[] mEndBytes;
	private int mPos=0;
	private boolean mDone=false;
	
	private final char mEndOfTransmission = '\u0004';
	
	protected XtremeStreamFilter(InputStream in)
	{
		super(in);
		// TODO Auto-generated constructor stub
		mPos = 0;
		mEndBytes = EncodingUtils.getBytes(mEnd, "UTF-8");
		mDone = false;
	}
	
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
		
		return val;//*/
		
		/*
		int val = super.read();
		if (val == mEndOfTransmission)
			return -1;
		else
			return val;//*/
	}
	
	@Override
	public int read(byte[] buffer, int offset, int count) throws IOException
	{/*
		int result = super.read(buffer, offset, count);
		if (result == -1)
			return -1;
		
		boolean done=false;
		for (int i=offset; i<offset+count; i++)
			if (done || buffer[i] == mEndOfTransmission)
			{
				done=true;
				buffer[i] = 0;
				
				if (i==offset)
					return -1;
			}
		
		return result;//*/
		
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
